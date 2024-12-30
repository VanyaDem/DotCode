package com.testtask.dotcode.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class AppError {

    private String timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;

    private AppError() {
    }

    public static AppError of(HttpStatus status, String message, WebRequest request) {
        AppError error = new AppError();
        error.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        error.status = status.value();
        error.error = status.getReasonPhrase();
        error.message = message;
        error.path = request.getDescription(false).replace("uri=", "");
        return error;
    }
}
