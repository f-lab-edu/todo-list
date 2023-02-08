package com.flab.todo.member;

import org.apache.ibatis.annotations.Mapper;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.flab.todo.database.entity.Member;

@Mapper
public interface MemberMapper {

	void save(Member member);

	Optional<Member> findByEmail(String email);

	void update(Member member);

	Member findByEmailAndEmailToken(@Param("email") String email, @Param("emailToken") String emailToken);
}
