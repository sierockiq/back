package com.quentin.sierocki.legume.back.controller.converter;

import com.quentin.sierocki.legume.back.exception.fonctionnal.FunctionnalException;

public class ConvertionException extends FunctionnalException {
	private static final long serialVersionUID = 1L;

	public ConvertionException(String message) {
		super(message);
	}
	
	public ConvertionException(String path,String message, Throwable cause) {
		super(path,message, cause);
	}

	public ConvertionException(String path,String message) {
		super(path,message);
	}
	
}
