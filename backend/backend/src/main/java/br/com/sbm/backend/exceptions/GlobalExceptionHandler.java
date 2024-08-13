package br.com.sbm.backend.exceptions;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidDateRangeException.class)
	public ResponseEntity<String> handleInvalidDateRangeException(InvalidDateRangeException idre) {
		return new ResponseEntity<>(idre.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<String> handleSQLException(SQLException se) {
		return new ResponseEntity<>(se.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
