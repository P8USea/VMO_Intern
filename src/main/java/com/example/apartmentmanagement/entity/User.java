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
    int id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    Set<Role> roles;

    @Column(name = "is_deleted")
    boolean is_Deleted = false;

    String firstName ;
    String lastName;
    String email;
    String phone;
}
