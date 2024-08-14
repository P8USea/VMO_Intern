package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ResidentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentService {

    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;

    public List<Resident> getResidentsByApartment( @NonNull int apartmentId){

        List<Resident> residents = residentRepository.findByApartmentId(apartmentId);
        if (residents.isEmpty()) {
            throw new EntityNotFoundException("No residents found for this apartment");
        }
        return residents;
    }
    public Resident getResidentById(@NonNull int residentId){
        return residentRepository.findById(residentId).orElseThrow(() -> new EntityNotFoundException("No residents found for this Id"));
    }

    public Resident createResident(Resident resident) {
        return residentRepository.save(resident);
    }
    public Resident updateResident(int id, Resident residentDetails){

        Resident resident = residentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Apartment not found with id " + id));
        resident.setName(residentDetails.getName());
        resident.setEmail(residentDetails.getEmail());
        resident.setPhoneNumber(residentDetails.getPhoneNumber());
        resident.setIdentityNumber(residentDetails.getIdentityNumber());
        resident.setBirthYear(residentDetails.getBirthYear());
        resident.setGender(residentDetails.getGender());
        return residentRepository.save(resident);
    }

    public void deleteResident(int id) {
        residentRepository.deleteById(id);
    }
}
