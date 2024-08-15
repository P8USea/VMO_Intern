package com.example.apartmentmanagement.exception;

public class LogicException extends BaseException {
    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
