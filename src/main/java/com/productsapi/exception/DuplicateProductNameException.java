package com.productsapi.exception;

public class DuplicateProductNameException extends RuntimeException {
	private static final long serialVersionUID = 8482992035851094177L;

	public DuplicateProductNameException(String errorMessage) {
        super(errorMessage);
    }
}
