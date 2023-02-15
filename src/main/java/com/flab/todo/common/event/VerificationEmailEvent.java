package com.flab.todo.common.event;

import org.springframework.context.ApplicationEvent;

import com.flab.todo.database.entity.Member;

import lombok.Getter;

@Getter
public class VerificationEmailEvent extends ApplicationEvent {

	private final Member member;

	public VerificationEmailEvent(Object source, Member member) {
		super(source);
		this.member = member;
	}
}
