package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.ApartmentCreationRequest;
import com.example.apartmentmanagement.dto.response.APIResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.service.ApartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
@Tag(name = "Apartment Controller")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;
    @Operation(summary = "Get all apartments")
    @GetMapping
    public List<Apartment> getAllApartments() {

        return apartmentService.getAllApartments();
    }
    @Operation(summary = "Get apartment by id")
    @GetMapping("/{id}")
    public Apartment getApartmentById(@PathVariable Integer id) {
        return apartmentService.getApartmentById(id);
    }
    @Operation(summary = "Add new apartments")
    @PostMapping
    public APIResponse<Object> addApartment(@RequestBody ApartmentCreationRequest request) {
        {
            return apartmentService.createApartment(request);
        }
    }
    @Operation(summary = "Update apartments by id")
    @PutMapping("/{id}")
    public Apartment updateApartment(@PathVariable Integer id, @RequestBody Apartment apartmentDetails) {
        return apartmentService.updateApartment(id, apartmentDetails);
    }
    @Operation(summary = "Delete apartments by id")
    @DeleteMapping("/{id}")
    public void deleteApartment(@PathVariable Integer id) {
        apartmentService.deleteApartment(id);
    }
}
