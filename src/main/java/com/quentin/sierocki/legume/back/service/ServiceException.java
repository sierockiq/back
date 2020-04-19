package com.quentin.sierocki.legume.back.service;

import com.quentin.sierocki.legume.back.exception.fonctionnal.FunctionnalException;

public class ServiceException extends FunctionnalException {

	private static final long serialVersionUID = 1L;

	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String path,String message, Throwable cause) {
		super(path,message, cause);
	}

	public ServiceException(String path,String message) {
		super(path,message);
	}

}
