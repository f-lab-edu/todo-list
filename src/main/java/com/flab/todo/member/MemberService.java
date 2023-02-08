package com.flab.todo.member;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flab.todo.common.dto.RequestSignUp;
import com.flab.todo.common.dto.UserDetailImpl;
import com.flab.todo.common.exception.custom.UnAuthorizedException;
import com.flab.todo.database.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final PasswordEncoder passwordEncoder;
	private final MemberMapper memberMapper;

	public void signUp(RequestSignUp requestSignUp) {
		validateDuplicateUser(requestSignUp);

		// encrypt password
		String encryptedPassword = passwordEncoder.encode(requestSignUp.getPassword());

		// convert RequestSignUp to Member
		Member member = RequestSignUp.from(requestSignUp, encryptedPassword);
		memberMapper.save(member);
	}

	private void validateDuplicateUser(RequestSignUp requestSignUp) {
		Member findMember = memberMapper.findByEmail(requestSignUp.getEmail());
		if (findMember != null) {
			throw new IllegalArgumentException("이미 가입된 회원입니다.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Member findMember = memberMapper.findByEmail(username);

		if(findMember == null)
			throw new UnAuthorizedException();

		return new UserDetailImpl(findMember);
	}
}
