package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.response.APIResponse;
import com.example.apartmentmanagement.dto.response.ApartmentCostResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.ServiceType;
import com.example.apartmentmanagement.entity.ServiceUsage;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.exception.ErrorCode;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ServiceTypeRepository;
import com.example.apartmentmanagement.repository.ServiceUsageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentCostService {
    final ApartmentRepository apartmentRepository;
    final ServiceTypeRepository serviceTypeRepository;
    final ServiceUsageRepository serviceUsageRepository;
    final ApartmentService apartmentService;

    public Optional<Apartment> calculateTotalCost(int apartmentId, YearMonth month) {
        Apartment apartment = apartmentService.getApartmentById(apartmentId);
        List<ServiceUsage> usages = serviceUsageRepository.findByApartmentAndMonth(apartmentId, month)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_USAGE_NOT_FOUND));
        apartment.setTotalCost(usages.stream().mapToDouble(ServiceUsage::getTotal).sum());
        return Optional.of(apartment);
    }

    public ApartmentCostResponse ApartmentMonthlyTotalCost(int apartmentId, YearMonth month) {
                Apartment apartment = apartmentService.getApartmentById(apartmentId);
                double totalCost = calculateTotalCost(apartmentId, month).get().getTotalCost();
                apartment.setTotalCost(totalCost);
                apartmentRepository.save(apartment);
                return ApartmentCostResponse.builder()
                                .apartmentCost(totalCost)
                                .apartmentId(apartmentId)
                                .build();

    }
    public Optional<ServiceUsage> calculateSingleCost(int apartmentId, int serviceTypeId, YearMonth month){
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_TYPE_NOT_FOUND));
        Apartment apartment = apartmentService.getApartmentById(apartmentId);
        Optional<ServiceUsage> usageOptional = serviceUsageRepository
                .findByApartmentAndServiceTypeAndMonth(apartmentId, serviceTypeId, month);

        usageOptional.ifPresent(usage -> {
            double totalCost = usageOptional.map(u -> u.getQuantity() * serviceType.getPricePerUnit()).orElse(0.0);
            usage.setTotal(totalCost);
            serviceUsageRepository.save(usage);
        });
        return usageOptional;
    }
    public ApartmentCostResponse ApartmentMonthlyServiceTypeCost(int apartmentId, int serviceTypeId, YearMonth month) {
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_TYPE_NOT_FOUND));
        Apartment apartment = apartmentService.getApartmentById(apartmentId);
        return ApartmentCostResponse.builder()
                        .serviceType(serviceType.getName())
                        .serviceTypeCost(calculateSingleCost(apartmentId, serviceTypeId, month).get().getTotal())
                        .apartmentId(apartmentId)
                        .build();

    }



}
