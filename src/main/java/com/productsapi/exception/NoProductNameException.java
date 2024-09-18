package com.productsapi.exception;

public class NoProductNameException extends RuntimeException {
	private static final long serialVersionUID = -941944772340680262L;

	public NoProductNameException(String errorMessage) {
        super(errorMessage);
    }
}
