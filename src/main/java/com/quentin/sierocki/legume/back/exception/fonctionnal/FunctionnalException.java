package com.quentin.sierocki.legume.back.exception.fonctionnal;

public class FunctionnalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pathMethod;

	public FunctionnalException( String message) {
		super(message);
	}
	
	public FunctionnalException(String pathMethod, String message) {
		super(message);
		this.pathMethod = pathMethod;
	}
	
	public FunctionnalException(String pathMethod,String message, Throwable cause) {
		super(message, cause);
		this.pathMethod = pathMethod;
	}

	public String getPathMethod() {
		return pathMethod;
	}

	public void setPathMethod(String pathMethod) {
		this.pathMethod = pathMethod;
	}

	
}
