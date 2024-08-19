package com.example.apartmentmanagement.entity;

import com.example.apartmentmanagement.entity.Manager;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.entity.ServiceUsage;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apartments")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int number = 3; //Số phòng != id phòng
    private double area = 6;
    private int rooms = 36;
    private boolean available = true;
    private int capacity = 363;
    private double totalCost = 3636;

    @OneToMany(mappedBy = "apartment")
    @JsonManagedReference
    private List<Resident> residents;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany(mappedBy = "apartment")
    private List<ServiceUsage> serviceUsages;
}
