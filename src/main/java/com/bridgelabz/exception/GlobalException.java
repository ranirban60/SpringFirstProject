package com.bridgelabz.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(value = UserException.class)
	@ResponseStatus
	@ResponseBody
	public ErrorResponse handleUserExistsException(UserException e) {
//		ErrorResponse errorResponse = new ErrorResponse(0, null, null);
//		errorResponse.setStatusCode(101);
//		errorResponse.setMessage(e.getMessage());
//		errorResponse.setDetails(e.getDetails());
		return new ErrorResponse(101, e.getMessage(), e.getDetails());
	}
}
