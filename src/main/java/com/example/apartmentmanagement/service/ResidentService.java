package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.response.ResidentResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.exception.ErrorCode;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResidentService {
    final ResidentRepository residentRepository;
    final ApartmentRepository apartmentRepository;

    public List<Resident> getResidentsByApartment(Integer apartmentId){

        List<Resident> residents = residentRepository.findByApartmentId(apartmentId);
        if (residents.isEmpty()) {
            throw new AppException(ErrorCode.RESIDENT_NOT_FOUND);
        }
        return residents;
    }


    public Resident getResidentById(Integer residentId){
        return residentRepository.findById(residentId).orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));
    }
    public void assignResident(Integer residentId, Integer apartmentId) {
        Resident resident = getResidentById(residentId);
        Apartment apartment = apartmentRepository.findById(apartmentId).orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));
        resident.setApartment(apartment);
        residentRepository.save(resident);
    }
    public void removeResident(Integer residentId) {
        Resident resident = getResidentById(residentId);
        resident.setApartment(null);
        residentRepository.save(resident);
    }

    public ResidentResponse ResidentToResponse(Resident resident) {
        return ResidentResponse.builder()
                .residentId(resident.getResidentId())
                .resident_ApartmentId(resident.getApartment().getId())
                .residentName(resident.getUser().getFirstName())
                .resident_UserId(resident.getUser().getId())
                .build();
    }
}
