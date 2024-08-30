package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.service.ResidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
@Tag(name = "Resident Controller")
public class ResidentController {

    @Autowired
    private ResidentService residentService;
    @Operation(summary = "Get residents by apartment id", description = "Get residents of the specified apartment ")
    @GetMapping("/apartment/{apartmentId}")
    public List<Resident> getResidentsByApartment(@PathVariable Integer apartmentId) {
        return residentService.getResidentsByApartment(apartmentId);
    }
    @Operation(summary = "Get resident's details by id")
    @GetMapping("/{residentId}")
    public Resident getResidentById(@PathVariable("residentId") Integer residentId) {
        return residentService.getResidentById(residentId);
    }

    @PostMapping
    public Resident createResident(@RequestBody Resident resident) {
        return residentService.createResident(resident);
    }

    /*@PutMapping("/{residentId}")
    public Resident updateResident(@PathVariable Long residentId, @RequestBody Resident resident) {
        resident.setId(residentId);
        return residentService.updateResident(residentId, resident);
    }*/
    @Operation(summary = "Delete resident")
    @DeleteMapping("/{residentId}")
    public void deleteResident(@PathVariable Integer residentId) {
        residentService.deleteResident(residentId);
    }
}
