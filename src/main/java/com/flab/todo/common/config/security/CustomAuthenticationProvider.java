package com.flab.todo.common.config.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.flab.todo.common.config.security.user_detail.UserDetailImpl;
import com.flab.todo.common.exception.custom.UnAuthorizedException;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;

		//입력한 ID, Password 조회
		String userId = token.getName();
		String userPw = (String)token.getCredentials();

		UserDetailImpl dbUser = null;
		//UserDetailsService를 통해 DB에서 조회한 사용자
		try {
			dbUser = (UserDetailImpl)userDetailsService.loadUserByUsername(userId);
		} catch (UnAuthorizedException ex) {
			throw new AuthenticationException("해당 이메일의 유저가 존재하지 않습니다.") {
			};
		}

		// 비밀번호 매칭되는지 확인
		if (!passwordEncoder.matches(userPw, dbUser.getPassword())) {
			throw new AuthenticationException("비밀번호가 틀렸습니다.") {
			};
		}

		// 아직 이메일 검증 코드를 입력하지 않은 경우
		if (!dbUser.getMember().isValid()) {
			throw new AuthenticationException("이메일 검증이 필요합니다.") {
			};
		}

		return new UsernamePasswordAuthenticationToken(dbUser, userPw, dbUser.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
