package com.norgini.exceptions;

public class InvalidAstrologDataException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidAstrologDataException(String message) {
		super(message);
	}

	public InvalidAstrologDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
