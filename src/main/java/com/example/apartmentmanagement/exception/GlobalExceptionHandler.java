package com.example.apartmentmanagement.exception;

import com.example.apartmentmanagement.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AppException.class)
    public ResponseEntity<APIResponse> handleUserException(AppException ex) {
        APIResponse response = new APIResponse();
        response.setMessage(ex.getMessage());
        response.setCode(ex.getErrorCode().getCode());
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(UsernameOrIdNotFound.class)
    public ResponseEntity<APIResponse> handleUserUserNameAndId(UserException ex) {
        APIResponse response = new APIResponse();
        response.setMessage(ex.getMessage());
        response.setCode(ex.getErrorCode().getCode());
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(CustomFieldValidationException.class)
    public ResponseEntity<APIResponse> handleSomething(UserException ex) {
        APIResponse response = new APIResponse();
        response.setMessage(ex.getMessage());
        response.setCode(ex.getErrorCode().getCode());
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleGeneralException(UserException ex) {
        APIResponse response = new APIResponse();
        response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        response.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        return ResponseEntity.badRequest().body(response);
    }
}
