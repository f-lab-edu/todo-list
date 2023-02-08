package com.flab.todo.common.config.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.flab.todo.common.exception.custom.UnAuthorizedException;
import com.flab.todo.member.MemberService;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final MemberService memberService;
	private final BCryptPasswordEncoder passwordEncoder;

	public CustomAuthenticationProvider(MemberService memberService,
		BCryptPasswordEncoder passwordEncoder) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

		//입력한 ID, Password 조회
		String userId = token.getName();
		String userPw = (String)token.getCredentials();

		UserDetailImpl dbUser = null;
		//UserDetailsService를 통해 DB에서 조회한 사용자
		try{
			dbUser = (UserDetailImpl) memberService.loadUserByUsername(userId);
		}catch (UnAuthorizedException ex){
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		// 비밀번호 매칭되는지 확인
		if (!passwordEncoder.matches(userPw, dbUser.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		// 아직 이메일 검증 코드를 입력하지 않은 경우
		if (!dbUser.getMember().isValid()){
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		return new UsernamePasswordAuthenticationToken(dbUser, userPw, dbUser.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
