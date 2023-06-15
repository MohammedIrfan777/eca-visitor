package com.eca.visitor.advice;

import com.eca.visitor.constants.VisitorConstants;
import com.eca.visitor.dto.response.ErrorResponse;
import com.eca.visitor.exception.UserNotFoundException;
import com.eca.visitor.exception.VisitorManagementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException ex) {
		log.error("GlobalExceptionHandler handleConstraintViolationException: {}", ex.getMessage());
		return new ResponseEntity<>(createErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(VisitorManagementException.class)
	public ResponseEntity<ErrorResponse> visitorManagementException(VisitorManagementException ex) {
		log.error(VisitorConstants.GLOBAL_EXCEPTION_HANDLER_KEY, ex.getMessage());
		return new ResponseEntity<>(createErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ErrorResponse> sqlException(SQLException ex) {
		log.error("GlobalExceptionHandler handleSqlException: {}", ex.getMessage());
		return new ResponseEntity<>(createErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> userNotFoundException(UserNotFoundException ex) {
		log.error("GlobalExceptionHandler handleUserNotFoundException: {}", ex.getMessage());
		return new ResponseEntity<>(createErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.error("GlobalExceptionHandler handleException: {}", ex.getMessage());
		return new ResponseEntity<>(createErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorResponse createErrorResponse(String errorMessage) {
		var errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setError(errorMessage);
		return errorResponse;
	}
}
