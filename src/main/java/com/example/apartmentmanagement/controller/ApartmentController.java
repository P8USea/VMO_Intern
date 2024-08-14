package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public List<Apartment> getAllApartments() {

        return apartmentService.getAllApartments();
    }

    @GetMapping("/{id}")
    public Apartment getApartmentById(@PathVariable int id) {
        return apartmentService.getApartmentById(id);
    }
    @PostMapping
    public Apartment addApartment(@RequestBody Apartment apartment) {
        {
            return apartmentService.createApartment(apartment);
        }
    }

    @PutMapping("/{id}")
    public Apartment updateApartment(@PathVariable int id, @RequestBody Apartment apartmentDetails) {
        return apartmentService.updateApartment(id, apartmentDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteApartment(@PathVariable int id) {
        apartmentService.deleteApartment(id);
    }
}
