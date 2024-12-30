package com.testtask.dotcode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppError> handleValidationExceptions(MethodArgumentNotValidException exception, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage())
                );

        String message = errors
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .reduce((e1, e2) -> e1 + ", " + e2)
                .orElse("");

        var error = AppError.of(HttpStatus.BAD_REQUEST, message, request);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<AppError> noSuchElementExceptionHandler(UserNotFoundException exception, WebRequest request) {
        var error = AppError.of(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserEmailExistException.class)
    public ResponseEntity<AppError> userEmailExistExceptionHandler(UserEmailExistException exception, WebRequest request) {
        var error = AppError.of(HttpStatus.CONFLICT, exception.getMessage(), request);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> exceptionHandler(RuntimeException exception, WebRequest request) {
        var error = AppError.of(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
