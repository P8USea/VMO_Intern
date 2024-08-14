package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @GetMapping("/apartment/{apartmentId}")
    public List<Resident> getResidentsByApartment(@PathVariable int apartmentId) {
        return residentService.getResidentsByApartment(apartmentId);
    }
    @GetMapping("/{residentId}")
    public Resident getResidentById(int residentId) {
        return residentService.getResidentById(residentId);
    }

    @PostMapping
    public Resident createResident(@RequestBody Resident resident) {
        return residentService.createResident(resident);
    }

    @PutMapping("/{residentId}")
    public Resident updateResident(@PathVariable int residentId, @RequestBody Resident resident) {
        resident.setId(residentId);
        return residentService.updateResident(residentId, resident);
    }

    @DeleteMapping("/{residentId}")
    public void deleteResident(@PathVariable int residentId) {
        residentService.deleteResident(residentId);
    }
}
