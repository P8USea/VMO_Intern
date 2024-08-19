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
    USER_NOT_EXISTED(105, "User Not Existed"),
    USER_NOT_FOUND(106, "User not found"),
    PASSWORD_INCORRECT(107, "Password Incorrect"),
    PASSWORD_MISMATCH(108, "Password Mismatch"),
    PASSWORD_REPEAT(109, "Password Repeat"),
    CONFIRMATION_CODE_MISMATCH(1010, "Confirmation Code Mismatch"),
    CONFIRMATION_CODE_BLANK(1011, "Confirmation Code Blank"),
    UNAUTHENTICATED_USER(1012, "Unauthenticated User"),

    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
