package com.example.apartmentmanagement.entity;

import com.example.apartmentmanagement.entity.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
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
    @Column(nullable = false)
    String confirmPassword;

    @Enumerated(EnumType.STRING)
    Role role;

    String firstName;
    String lastName;
    String email;
    String phone;
}
