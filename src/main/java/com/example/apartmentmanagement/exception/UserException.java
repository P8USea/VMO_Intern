package com.example.apartmentmanagement.exception;

import lombok.Getter;

@Getter

public class UserException extends RuntimeException{
    ErrorCode errorCode;
    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
