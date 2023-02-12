package com.flab.todo.member;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.flab.todo.database.entity.Member;

@Mapper
public interface MemberMapper {

	void save(Member member);

	boolean existsByEmail(String email);

	Optional<Member> findByEmail(String email);

	void update(Member member);

	Member findByEmailAndEmailToken(@Param("email") String email, @Param("emailToken") String emailToken);
}
