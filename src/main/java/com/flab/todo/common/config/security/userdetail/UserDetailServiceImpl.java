package com.flab.todo.common.config.security.userdetail;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.flab.todo.common.exception.custom.UnAuthorizedException;
import com.flab.todo.database.entity.Member;
import com.flab.todo.member.MemberMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private final MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Member findMember = memberMapper.findByEmail(username).orElseThrow(UnAuthorizedException::new);
		return new UserDetailImpl(findMember);
	}
}
