package com.example.apartmentmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private int number;
    @Column(length = 50)
    private double area;
    @Column(length = 50)
    private int rooms;

    @OneToMany(mappedBy = "apartment")
    private List<Resident> residents;

}
