package com.bridgelabz.exception;

public class ErrorResponse {

	private int statusCode;
	private String message;
	private String details;

	public ErrorResponse(int code, String msg, String details) {
		this.setStatusCode(code);
		this.setMessage(msg);
		this.setDetails(details);
	}


	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
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
