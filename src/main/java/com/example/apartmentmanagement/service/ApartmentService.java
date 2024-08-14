package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Apartment getApartmentById(int id) {
        return apartmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Apartment not found with id " + id));
    }
    public Apartment createApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment(int id, Apartment apartmentDetails) {
        Apartment apartment = apartmentRepository.findById(id).orElse(null);
        if (apartment != null) {
            apartment.setId(apartmentDetails.getId());
            apartment.setArea(apartmentDetails.getArea());
            apartment.setRooms(apartmentDetails.getRooms());
            return apartmentRepository.save(apartment);
        }
        return null;
    }

    public void deleteApartment(int id) {
        apartmentRepository.deleteById(id);
    }
}
