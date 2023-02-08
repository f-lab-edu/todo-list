package com.flab.todo.common.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD, TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = MatchPasswordValidator.class)
public @interface MatchPassword {
	String message() default "패스워드가 일치하지 않습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}