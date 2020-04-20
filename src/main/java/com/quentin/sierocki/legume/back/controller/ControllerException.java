package com.quentin.sierocki.legume.back.controller;

import com.quentin.sierocki.legume.back.exception.fonctionnal.FunctionnalException;

public class ControllerException extends FunctionnalException {

	private static final long serialVersionUID = 1L;

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(String messageRetour,String path, String message, Throwable cause) {
		super(messageRetour,path, message, cause);
	}

	public ControllerException(String messageRetour,String path, String message) {
		super(messageRetour,path, message);
	}

}
