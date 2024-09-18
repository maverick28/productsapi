package com.productsapi.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.productsapi.exception.DuplicateProductNameException;
import com.productsapi.exception.NoProductNameException;
import com.productsapi.exception.NotFoundProductException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NotFoundProductException.class})
    public ResponseEntity<Object> handleStudentNotFoundException(NotFoundProductException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
    @ExceptionHandler({DuplicateProductNameException.class})
    public ResponseEntity<Object> handleStudentAlreadyExistsException(DuplicateProductNameException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
    @ExceptionHandler({NoProductNameException.class})
    public ResponseEntity<Object> handleStudentAlreadyExistsException(NoProductNameException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
