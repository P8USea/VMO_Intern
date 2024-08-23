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

    public Apartment updateApartment(int id, Apartment apartmentDetails) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));
            apartment.setId(apartmentDetails.getId());
            apartment.setArea(apartmentDetails.getArea());
            apartment.setRooms(apartmentDetails.getRooms());
            apartment.setCapacity(apartmentDetails.getCapacity());
            apartment.setAvailable(apartmentDetails.isAvailable());
            return apartmentRepository.save(apartment);

    }

    public void deleteApartment(int apartmentId) {
        apartmentRepository.deleteById(apartmentId);
    }
    public List<ServiceType> getServiceTypes() {
        return serviceTypeRepository.findAll();
    }
    public void initializeServiceUsageForApartments(List<Apartment> apartments, List<ServiceType> serviceTypes, YearMonth month) {
        for (Apartment apartment : apartments) {
            for (ServiceType serviceType : serviceTypes) {
                if (!serviceUsageRepository.existsByApartmentIdAndServiceTypeIdAndMonth(apartment.getId(), serviceType.getId(), month)) {
                    ServiceUsage serviceUsage = new ServiceUsage();
                    serviceUsage.setApartment(apartment);
                    serviceUsage.setServiceType(serviceType);
                    serviceUsage.setMonth(month);
                    serviceUsage.setQuantity(10); // Giá trị mặc định
                    serviceUsage.setTotal(0); // Giá trị mặc định

                    serviceUsageRepository.save(serviceUsage);
                }
            }
        }
    }
}
