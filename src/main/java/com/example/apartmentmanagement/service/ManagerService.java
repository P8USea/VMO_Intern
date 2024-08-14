package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.ServiceType;
import com.example.apartmentmanagement.entity.ServiceUsage;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ServiceTypeRepository;
import com.example.apartmentmanagement.repository.ServiceUsageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    ServiceUsageRepository serviceUsageRepository;
    @Autowired
    ApartmentRepository apartmentRepository;
    @Autowired
    ServiceTypeRepository serviceTypeRepository;


    public void assignServiceToApartment(int apartmentId, int serviceTypeId, double quantity) {
        Apartment apartment = apartmentRepository.findById(apartmentId).orElseThrow(() -> new EntityNotFoundException("Apartment not found"));
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId).orElseThrow(() -> new EntityNotFoundException("ServiceType not found"));

        ServiceUsage serviceUsage = new ServiceUsage();
        serviceUsage.setApartment(apartment);
        serviceUsage.setServiceType(serviceType);
        serviceUsage.setQuantity(quantity);

        serviceUsageRepository.save(serviceUsage);
    }

    public double calculateTotalExpense() {
        List<Apartment> apartments = apartmentRepository.findAll();
        return apartments.stream()
                .flatMap(apartment -> apartment.getServiceUsages().stream())
                .mapToDouble(usage -> usage.getQuantity() * usage.getServiceType().getPricePerUnit())
                .sum();
    }
    public double calculateTotalExpenseForMonth(YearMonth month) {
        List<ServiceUsage> usages = serviceUsageRepository.findByMonth(month);
        return usages.stream()
                .mapToDouble(usage -> usage.getQuantity() * usage.getServiceType().getPricePerUnit())
                .sum();
    }

}
