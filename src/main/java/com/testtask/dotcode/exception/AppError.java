package com.testtask.dotcode.exception;

import lombok.Data;

@Data
public class AppError {

    private int status;

    private String message;

    private AppError() {
    }

    public static AppError of(int status, String message) {
        AppError error = new AppError();
        error.setStatus(status);
        error.setMessage(message);
        return error;
    }
}
