package com.bridgelabz.exception;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String message;
	String details;

	public UserException() {

	}

	public UserException(String message, String details) {
		this.message = message;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
