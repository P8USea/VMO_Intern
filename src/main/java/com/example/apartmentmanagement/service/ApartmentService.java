package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.request.ApartmentCreationRequest;
import com.example.apartmentmanagement.dto.response.APIResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.ServiceType;
import com.example.apartmentmanagement.entity.ServiceUsage;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.exception.ErrorCode;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ServiceTypeRepository;
import com.example.apartmentmanagement.repository.ServiceUsageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentService {

    final ApartmentRepository apartmentRepository;
    final ServiceTypeRepository serviceTypeRepository;
    final ServiceUsageRepository serviceUsageRepository;
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public Apartment getApartmentById(int apartmentId) {
        return apartmentRepository.findById(apartmentId).orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));
    }

    public APIResponse<Object> createApartment(ApartmentCreationRequest request) {
        Apartment apartment = Apartment.builder()
                .number(request.getNumber())
                .rooms(request.getRooms())
                .area(request.getArea())
                .capacity(request.getCapacity())
                .available(request.isAvailable())
                .build();
        apartmentRepository.save(apartment);
        return APIResponse.builder()
                .result("New apartment created")
                .build();
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Apartment updateApartment(int id, Apartment apartmentDetails) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));
        apartment.setId(apartmentDetails.getId());
        apartment.setArea(apartmentDetails.getArea());
        apartment.setRooms(apartmentDetails.getRooms());
        apartment.setCapacity(apartmentDetails.getCapacity());
        apartment.setAvailable(apartmentDetails.isAvailable());
        return apartmentRepository.save(apartment);

    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteApartment(int apartmentId) {
        apartmentRepository.deleteById(apartmentId);
    }


}
