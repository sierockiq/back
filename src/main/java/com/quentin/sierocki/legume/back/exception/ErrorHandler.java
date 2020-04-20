package com.quentin.sierocki.legume.back.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.quentin.sierocki.legume.back.controller.ControllerException;
import com.quentin.sierocki.legume.back.controller.model.ValidationException;
import com.quentin.sierocki.legume.back.jwt.ResourceNotFoundException;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public final CustomErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return new CustomErrorResponse("La ressource demandée est introuvable.");
	}

	@ExceptionHandler(ControllerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public final CustomErrorResponse handleFunctionnalException(ControllerException ex, WebRequest request) {
		return new CustomErrorResponse(ex.getMessageRetour());
	}
	
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public final CustomErrorResponse handleFunctionnalException(ValidationException ex, WebRequest request) {
		return new CustomErrorResponse(ex.getMessageRetour());
	}

}
