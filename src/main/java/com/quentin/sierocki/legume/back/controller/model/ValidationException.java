package com.quentin.sierocki.legume.back.controller.model;

import com.quentin.sierocki.legume.back.exception.fonctionnal.FunctionnalException;

public class ValidationException extends FunctionnalException {
	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
		super(message);
	}
	
	public ValidationException(String path,String message, Throwable cause) {
		super(path,message, cause);
	}

	public ValidationException(String path,String message) {
		super(path,message);
	}
	
}
