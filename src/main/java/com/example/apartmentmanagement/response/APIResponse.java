package com.example.apartmentmanagement.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class APIResponse <T> {
    private int code;
    private String message;
    private T result;
}
