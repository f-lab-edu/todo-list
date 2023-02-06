package com.flab.todo.member;

import org.apache.ibatis.annotations.Mapper;

import com.flab.todo.database.entity.Member;

@Mapper
public interface MemberMapper {

	void save(Member member);

	Member findByEmail(String email);
}
