package com.quentin.sierocki.legume.back.jwt;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException( String resourceName, String fieldName, Object fieldValue) {
        // adding a message
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
    }

}
