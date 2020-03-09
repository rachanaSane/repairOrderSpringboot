package com.auto.repairorder.error;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomErrorHandler extends ResponseEntityExceptionHandler {

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> generationExceptionHandler(Exception e) {

		ErrorResponse errors = new ErrorResponse();
		errors.setError(e.getMessage());
		errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(RepairOrderNotFoundException.class)
	public ResponseEntity<ErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {

		ErrorResponse errors = new ErrorResponse();
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(RepairOrderException.class)
	public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex, WebRequest request) {

		ErrorResponse errors = new ErrorResponse();
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();

		body.put("status", status.value());

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);

	}

}
