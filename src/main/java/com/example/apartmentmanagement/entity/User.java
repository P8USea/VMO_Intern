package com.example.apartmentmanagement.entity;

import com.example.apartmentmanagement.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;
    String confirmPassword;

    Set<Role> roles;

    String firstName;
    String lastName;
    String email;
    String phone;
}
