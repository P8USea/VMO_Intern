package com.example.apartmentmanagement.exception;

import com.example.apartmentmanagement.dto.response.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
    @ExceptionHandler(UserException.class)
    public ResponseEntity<APIResponse> handleUserUserNameAndIdException(UserException ex) {
        APIResponse response = new APIResponse();
        response.setMessage(ex.getMessage());
        response.setCode(ex.getErrorCode().getCode());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatus()).body(
                APIResponse.builder()
                        .message(errorCode.getMessage())
                        .code(errorCode.getCode())
                        .build()
        );
    }
    @ExceptionHandler(CustomFieldValidationException.class)
    public ResponseEntity<APIResponse> handleSomething(UserException ex) {
        APIResponse response = new APIResponse();
        response.setMessage(ex.getMessage());
        response.setCode(ex.getErrorCode().getCode());
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleGeneralException(Exception ex) {
        APIResponse response = new APIResponse();
        response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        response.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        return ResponseEntity.badRequest().body(response);
    }
}
