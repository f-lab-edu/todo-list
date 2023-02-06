package com.flab.todo.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flab.todo.common.dto.RequestSignUp;

public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		RequestSignUp passwordConfirm = (RequestSignUp)value;
		return passwordConfirm.getPassword().equals(((RequestSignUp)value).getPasswordConfirm());
	}
}
