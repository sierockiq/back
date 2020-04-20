package com.quentin.sierocki.legume.back.controller.model;

import com.quentin.sierocki.legume.back.exception.fonctionnal.FunctionnalException;

public class ValidationException extends FunctionnalException {
	private static final long serialVersionUID = 1L;

	public ValidationException(String messageRetour,String message) {
		super(message);
		this.setMessageRetour(messageRetour);
	}

	public ValidationException(String messageRetour, String path, String message, Throwable cause) {
		super(messageRetour, path, message, cause);
	}

	public ValidationException(String messageRetour, String path, String message) {
		super(messageRetour, path, message);
	}

}
