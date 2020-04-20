package com.quentin.sierocki.legume.back.exception.fonctionnal;

public class FunctionnalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pathMethod;
	private String messageRetour;
	public FunctionnalException( String message) {
		super(message);
	}
	
	public FunctionnalException(String messageRetour,String pathMethod, String message) {
		super(message);
		this.pathMethod = pathMethod;
		this.messageRetour = messageRetour;
	}
	
	public FunctionnalException(String messageRetour,String pathMethod,String message, Throwable cause) {
		super(message, cause);
		this.pathMethod = pathMethod;
		this.messageRetour = messageRetour;
	}

	public String getPathMethod() {
		return pathMethod;
	}

	public void setPathMethod(String pathMethod) {
		this.pathMethod = pathMethod;
	}

	public String getMessageRetour() {
		return messageRetour;
	}

	public void setMessageRetour(String messageRetour) {
		this.messageRetour = messageRetour;
	}
	

	
}
