package com.example.apartmentmanagement.entity;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "managers")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    private String name = "F";
    private String phoneNumber = "636";

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
