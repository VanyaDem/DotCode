package com.testtask.dotcode.exception;

public class UserEmailExistException extends RuntimeException {
  public UserEmailExistException(String message,Throwable cause) {
    super(message,cause);
  }
}
