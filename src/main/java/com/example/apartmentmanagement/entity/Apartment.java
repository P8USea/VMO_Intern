package com.example.apartmentmanagement.entity;

import com.example.apartmentmanagement.entity.Manager;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.entity.ServiceUsage;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apartments")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int number; //Số phòng != id phòng
    double area;
    int rooms;
    boolean available;
    int capacity;
    double totalCost;

    @OneToMany(mappedBy = "apartment")
    @JsonManagedReference
    List<Resident> residents;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    Manager manager;

    @OneToMany(mappedBy = "apartment")
    @JsonIgnore
    List<ServiceUsage> serviceUsages;
}
