package com.testtask.dotcode.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AppError {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    private AppError() {
    }

    public static AppError of(int status, String message) {
        AppError error = new AppError();
        error.setStatus(status);
        error.setMessage(message);
        error.setTimestamp(LocalDateTime.now());
        return error;
    }
}
