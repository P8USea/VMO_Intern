package com.example.apartmentmanagement.exception;

import lombok.Getter;

@Getter
public class CustomFieldValidationException extends Exception {

    private final String fieldName;

    public CustomFieldValidationException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

}