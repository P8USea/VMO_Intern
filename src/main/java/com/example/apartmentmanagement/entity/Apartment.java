package com.example.apartmentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private int number; //Số phòng != id phòng
    @Column(length = 50)
    private double area;
    @Column(length = 50)
    private int rooms;
    @Column
    private boolean available;
    @Column(length = 50)
    private int capacity;


    @OneToMany(mappedBy = "apartment")
    @JsonManagedReference
    private List<Resident> residents;

    @ManyToOne
    private Manager manager;

    @OneToMany(mappedBy = "apartment")
    private List<ServiceUsage> serviceUsages;
    @Column(length = 50)
    private double totalCost;


}
