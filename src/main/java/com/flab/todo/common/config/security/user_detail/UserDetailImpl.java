package com.flab.todo.common.config.security.user_detail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.flab.todo.database.entity.Member;

public class UserDetailImpl implements UserDetails, Serializable {
	private final Member member;

	public UserDetailImpl(Member member) {
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

	public Long getUserId() {
		return member.getId();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "UserDetailImpl{" + "member=" + member + '}';
	}
}
