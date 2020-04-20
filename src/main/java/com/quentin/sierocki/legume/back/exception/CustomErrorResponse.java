package com.quentin.sierocki.legume.back.exception;

public class CustomErrorResponse {
	private String message;
	private boolean error;
	CustomErrorResponse(String message) {
		this.message = message;
		this.error = true;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}

	
}
