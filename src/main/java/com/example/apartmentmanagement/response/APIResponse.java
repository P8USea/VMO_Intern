package com.example.apartmentmanagement.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIResponse <T> {
    private int code;
    private String message;
    private T result;
}
