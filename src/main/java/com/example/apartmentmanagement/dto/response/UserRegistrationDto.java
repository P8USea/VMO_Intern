package com.example.apartmentmanagement.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserRegistrationDto {
    private String username;
    private String password;
    private String confirmPassword;

}
