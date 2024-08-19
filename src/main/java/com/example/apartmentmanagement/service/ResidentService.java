package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.exception.ErrorCode;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentService {

    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;

    public List<Resident> getResidentsByApartment(Long apartmentId){

        List<Resident> residents = residentRepository.findByApartmentId(apartmentId);
        if (residents.isEmpty()) {
            throw new AppException(ErrorCode.RESIDENT_NOT_FOUND);
        }
        return residents;
    }
    public Resident getResidentById(Long residentId){
        return residentRepository.findById(residentId).orElseThrow(() -> new AppException(ErrorCode.NO_RESIDENTS_IN_APARTMENT));
    }

    public Resident createResident(Resident resident) {
        return residentRepository.save(resident);
    }
    /*public Resident updateResident(Long id, Resident residentDetails){

        Resident resident = residentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));
        resident.setName(residentDetails.getName());
        resident.setEmail(residentDetails.getEmail());
        resident.setPhoneNumber(residentDetails.getPhoneNumber());
        resident.setIdentityNumber(residentDetails.getIdentityNumber());
        resident.setBirthYear(residentDetails.getBirthYear());
        resident.setGender(residentDetails.getGender());
        return residentRepository.save(resident);
    }*/

    public void deleteResident(Long id) {
        residentRepository.deleteById(id);
    }
}
