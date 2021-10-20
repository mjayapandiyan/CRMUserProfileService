package com.crm.app.user.profile.exception;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.crm.app.user.profile.dto.ErrorDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	  public ResponseEntity<ErrorDto> handleMaxSizeException(MaxUploadSizeExceededException e, WebRequest request) {
		ErrorDto response = new ErrorDto();
        response.setMessage(e.getLocalizedMessage());
        response.setStatusCode(HttpStatus.EXPECTATION_FAILED);
        response.setPath(request.getContextPath());
        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
	  }
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class) 
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleBadInputRequestException(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		 e.getBindingResult().getAllErrors().forEach(error -> {
		        String fieldName = ((FieldError) error).getField();
		        String errorMessage = error.getDefaultMessage();
		        errors.put(fieldName, errorMessage);
		    });
		
		 return errors;
	}

	@ExceptionHandler(value = InvalidRecordException.class)
	public ResponseEntity<ErrorDto> handleUserInputException(InvalidRecordException e, WebRequest request) {
		ErrorDto response = new ErrorDto();
        response.setMessage(e.getLocalizedMessage());
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.setPath(request.getContextPath());
        response.setTimeStamp(ZonedDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
