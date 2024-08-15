package com.example.apartmentmanagement.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ErrorResponse {
    private Date timestamp;
    private String message;
    private int status;

}
