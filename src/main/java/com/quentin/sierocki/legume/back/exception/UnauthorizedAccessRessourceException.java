package com.quentin.sierocki.legume.back.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UnauthorizedAccessRessourceException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnauthorizedAccessRessourceException(String id,String idUser ) {
        // adding a message
        super(String.format("User "+id+" is not authorized to access datas of user "+idUser));
    }

}
