package com.norgini.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.norgini.dtos.ErrorResponse;
import com.norgini.exceptions.InvalidAstrologDataException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidAstrologDataException.class)
	public ResponseEntity<ErrorResponse> handleInvalidAstrologData(InvalidAstrologDataException ex) {
		ErrorResponse error = new ErrorResponse("Dados Astrológicos Inválidos", ex.getMessage(),
				HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
		String errorMessage = ex.getBindingResult().getFieldErrors().stream()
				.map(org.springframework.validation.FieldError::getDefaultMessage).findFirst()
				.orElse("Parâmetros de requisição inválidos");
		ErrorResponse error = new ErrorResponse("Erro de Validação", errorMessage, HttpStatus.BAD_REQUEST.value(),
				LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
