package com.testtask.dotcode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppError> handleValidationExceptions(MethodArgumentNotValidException exception, WebRequest request) {
        var message = getErrorMessage(exception);
        var error = AppError.of(HttpStatus.BAD_REQUEST, message, request);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<AppError> userNotFoundHandler(UserNotFoundException exception, WebRequest request) {
        var error = AppError.of(HttpStatus.NOT_FOUND, exception.getMessage(), request);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
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

    private static String getErrorMessage(MethodArgumentNotValidException exception) {
        return exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
    }
}
