package com.example.apartmentmanagement.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(666, "Uncategorized Exception"),
    SERVICE_TYPE_NOT_FOUND(100, "Service Type Not Found"),
    APARTMENT_NOT_FOUND(101, "Apartment Not Found"),
    RESIDENT_NOT_FOUND(102, "Resident Not Found"),
    NO_RESIDENTS_IN_APARTMENT(103, "No Resident in Apartment"),
    USER_EXISTED(104, "User Existed"),
    USER_NOT_FOUND(105, "User not found"),
    PASSWORD_INCORRECT(106, "Password Incorrect"),
    PASSWORD_MISMATCH(107, "Password Mismatch"),
    PASSWORD_REPEAT(108, "Password Repeat"),
    CONFIRMATION_CODE_MISMATCH(109, "Confirmation Code Mismatch"),
    CONFIRMATION_CODE_BLANK(1010, "Confirmation Code Blank"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
