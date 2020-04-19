package com.quentin.sierocki.legume.back.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.quentin.sierocki.legume.back.controller.converter.ConvertionException;
import com.quentin.sierocki.legume.back.exception.fonctionnal.FunctionnalException;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public final CustomErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return this.getCustomErrorResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
	}
	
	@ExceptionHandler(FunctionnalException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public final CustomErrorResponse handleFunctionnalException(FunctionnalException ex, WebRequest request) {
		return this.getCustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
	}
	
	@ExceptionHandler(ConvertionException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public final CustomErrorResponse handleFunctionnalException(ConvertionException ex, WebRequest request) {
		return this.getCustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
	}
	
	

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String,String> errors = new HashMap<String,String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField() , error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.put(error.getObjectName() , error.getDefaultMessage());
		}
		logger.warn(ex.getLocalizedMessage());
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}


	private CustomErrorResponse getCustomErrorResponse(HttpStatus httpStatus, String message) {
		return new CustomErrorResponse(httpStatus.getReasonPhrase(), httpStatus.value(), message);
	}

}
