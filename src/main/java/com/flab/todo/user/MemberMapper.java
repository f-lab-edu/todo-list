package com.flab.todo.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	Member insert(Member req);
}
