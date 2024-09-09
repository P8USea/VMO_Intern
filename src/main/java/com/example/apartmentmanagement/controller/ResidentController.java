package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.response.APIResponse;
import com.example.apartmentmanagement.dto.response.ResidentResponse;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.repository.ResidentRepository;
import com.example.apartmentmanagement.service.ResidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/residents")
@Tag(name = "Resident Controller")
public class ResidentController {

    @Autowired
    private ResidentService residentService;
    @Autowired
    private ResidentRepository residentRepository;

    @Operation(summary = "Get residents by apartment id", description = "Get residents of the specified apartment ")
    @GetMapping("/apartment/{apartmentId}")
    public APIResponse<Object> getResidentsByApartment(@PathVariable Integer apartmentId) {
        var result = residentService.getResidentsByApartment(apartmentId);
        List<ResidentResponse> residents = new ArrayList<>();
        for (Resident resident : result) {
            residents.add(residentService.ResidentToResponse(resident));
        }
        return APIResponse.builder()
                .result(residents)
                .build();
    }
    @Operation(summary = "Get resident's details by id")
    @GetMapping("/{residentId}")
    public APIResponse<ResidentResponse> getResidentById(@PathVariable("residentId") Integer residentId) {
        var result = residentService.getResidentById(residentId);
        return APIResponse.<ResidentResponse>builder()
                .result(residentService.ResidentToResponse(result))
                .build();
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
    @PostMapping("/assign/{residentId}-{apartmentId}")
    public APIResponse<Object> assignResident(
            @PathVariable int residentId
            , @PathVariable int apartmentId){
        residentService.assignResident(residentId, apartmentId);
        return APIResponse.builder()
                .result("Assigned resident with id " + residentId + " to apartment with id " + apartmentId)
                .build();
    }
    @PostMapping("/remove/{residentId}")
    public APIResponse<Object> removeResident(@PathVariable Integer residentId){
        int apartmentId = residentRepository.findById(residentId).get().getApartment().getId();
        residentService.removeResident(residentId);
        return APIResponse.builder()
                .result("Removed resident with id " + residentId + "from apartment with id " + apartmentId)
                .build();
    }
}
