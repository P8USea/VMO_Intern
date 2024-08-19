package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @GetMapping("/apartment/{apartmentId}")
    public List<Resident> getResidentsByApartment(@PathVariable Long apartmentId) {
        return residentService.getResidentsByApartment(apartmentId);
    }
    @GetMapping("/{residentId}")
    public Resident getResidentById(@PathVariable("residentId") Long residentId) {
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

    @DeleteMapping("/{residentId}")
    public void deleteResident(@PathVariable Long residentId) {
        residentService.deleteResident(residentId);
    }
}
