package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.ServiceType;
import com.example.apartmentmanagement.entity.ServiceUsage;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ServiceTypeRepository;
import com.example.apartmentmanagement.repository.ServiceUsageRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApartmentCostService {

    @Autowired
    private ServiceUsage usage;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private ServiceType serviceType;
    @Autowired
    private ServiceUsageRepository serviceUsageRepository;

    public double ApartmentMonthlyServiceTypeCost(int apartmentId, int serviceTypeId, YearMonth month) {
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new EntityNotFoundException("ServiceType not found"));
        Apartment apartment = apartmentRepository.findById(apartmentId).orElse(null);

        Optional<ServiceUsage> usage = serviceUsageRepository.findByApartmentAndServiceTypeAndMonth(apartment, serviceType, month);

        return usage.map(u -> u.getQuantity() * serviceType.getPricePerUnit())
                .orElse(0.0);
    }



    public double ApartmentMonthlyTotalCost(int apartmentId, YearMonth month) {
        List<ServiceUsage> usages = serviceUsageRepository.findByApartmentAndMonth(apartmentId, month);
        return usages.stream().mapToDouble(ServiceUsage::getTotal).sum();
    }

    public Apartment ApartmentMonthlyTotalCostAssignment(int apartmentId, YearMonth month) {
                Apartment apartment = apartmentRepository.findById(apartmentId).orElse(null);
                double totalCost = ApartmentMonthlyTotalCost(apartmentId, month);
                apartment.setTotalCost(totalCost);
                return apartmentRepository.save(apartment);

    }
    public void ApartmentMonthlyServiceTypeCostAssignment(int apartmentId, int serviceTypeId, YearMonth month) {
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new EntityNotFoundException("ServiceType not found"));
        Apartment apartment = apartmentRepository.findById(apartmentId).orElse(null);
        Optional<ServiceUsage> usageOptional = serviceUsageRepository.findByApartmentAndServiceTypeAndMonth(apartment, serviceType, month);

        usageOptional.ifPresent(usage -> {
            double totalCost = ApartmentMonthlyServiceTypeCost(apartmentId, serviceTypeId, month);
            usage.setTotal(totalCost);
            serviceUsageRepository.save(usage);
        });
    }

}
