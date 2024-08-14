package com.example.apartmentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "manager")
    private List<Apartment> apartments;
}
