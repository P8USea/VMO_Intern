package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.exception.ErrorCode;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public Apartment getApartmentById(int apartmentId) {
        return apartmentRepository.findById(apartmentId).orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));
    }
    public Apartment createApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment(int id, Apartment apartmentDetails) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));
        if (apartment != null) {
            apartment.setId(apartmentDetails.getId());
            apartment.setArea(apartmentDetails.getArea());
            apartment.setRooms(apartmentDetails.getRooms());
            return apartmentRepository.save(apartment);
        }
        return null;
    }

    public void deleteApartment(int apartmentId) {
        apartmentRepository.deleteById(apartmentId);
    }
}
