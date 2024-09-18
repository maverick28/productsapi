package com.productsapi.exception;

public class NotFoundProductException extends RuntimeException {
	private static final long serialVersionUID = -6989219770694438124L;

	public NotFoundProductException(String errorMessage) {
        super(errorMessage);
    }
}
