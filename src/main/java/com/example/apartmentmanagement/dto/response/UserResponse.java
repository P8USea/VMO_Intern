package com.example.apartmentmanagement.dto.response;


import com.example.apartmentmanagement.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    int id;
    String firstName;
    String lastName;
    String email;
    String phone;
    Set<Role> role;


}
