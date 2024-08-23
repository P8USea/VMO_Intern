package com.example.apartmentmanagement.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@JsonIgnoreProperties
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(666, "Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_TYPE_NOT_FOUND(100, "Service Type Not Found", HttpStatus.NOT_FOUND),
    APARTMENT_NOT_FOUND(101, "Apartment Not Found", HttpStatus.NOT_FOUND),
    RESIDENT_NOT_FOUND(102, "Resident Not Found", HttpStatus.NOT_FOUND),
    NO_RESIDENTS_IN_APARTMENT(103, "No Resident in Apartment", HttpStatus.NOT_FOUND),
    USER_EXISTED(104, "User Existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(105, "User Not Existed", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(106, "User not found", HttpStatus.NOT_FOUND),
    PASSWORD_INCORRECT(107, "Password Incorrect", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH(108, "Password Mismatch", HttpStatus.BAD_REQUEST),
    PASSWORD_REPEAT(109, "Password Repeat", HttpStatus.BAD_REQUEST),
    CONFIRMATION_CODE_MISMATCH(1010, "Confirmation Code Mismatch", HttpStatus.BAD_REQUEST),
    CONFIRMATION_CODE_BLANK(1011, "Confirmation Code Blank", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1012, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1013, "Unauthorized", HttpStatus.FORBIDDEN),
    SERVICE_USAGE_NOT_FOUND(1014, "Service Usage Not Found", HttpStatus.NOT_FOUND),
    CONFIRM_PASSWORD_MISMATCH(1015, "Confirm Password Mismatch", HttpStatus.BAD_REQUEST);
    private int code;
    private String message;
    private HttpStatus status;
    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
