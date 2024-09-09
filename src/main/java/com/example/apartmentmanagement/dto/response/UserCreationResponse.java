package com.example.apartmentmanagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationResponse {
    int userId;
    String userName;
    String message = "User ${userName} has been created";
}
