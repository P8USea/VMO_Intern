package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.ServiceType;
import com.example.apartmentmanagement.entity.ServiceUsage;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.exception.ErrorCode;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ServiceTypeRepository;
import com.example.apartmentmanagement.repository.ServiceUsageRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ApartmentCostService {
    ServiceUsage usage;
    ApartmentRepository apartmentRepository;
    ServiceTypeRepository serviceTypeRepository;
    ServiceType serviceType;
    ServiceUsageRepository serviceUsageRepository;
    ApartmentService apartmentService;

//Tiền dịch vụ ứng với loại dịch vụ hàng tháng của 1 căn hộ trong 1 tháng
    public double ApartmentMonthlyServiceTypeCost(int apartmentId, int serviceTypeId, YearMonth month) {
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_TYPE_NOT_FOUND));
        Apartment apartment = apartmentService.getApartmentById(apartmentId);

        Optional<ServiceUsage> usage = serviceUsageRepository.findByApartmentAndServiceTypeAndMonth(apartment, serviceType, month);

        return usage.map(u -> u.getQuantity() * serviceType.getPricePerUnit())
                .orElse(0.0);
    }


//Tổng tiền dịch vụ của 1 căn hộ trong 1 tháng
    public double ApartmentMonthlyTotalCost(int apartmentId, YearMonth month) {
        List<ServiceUsage> usages = serviceUsageRepository.findByApartmentAndMonth(apartmentId, month);
        return usages.stream().mapToDouble(ServiceUsage::getTotal).sum();
    }
//Gán tiền dịch vụ cho 1 căn hộ
    public Apartment ApartmentMonthlyTotalCostAssignment(int apartmentId, YearMonth month) {
                Apartment apartment = apartmentRepository.findById(apartmentId).orElse(null);
                double totalCost = ApartmentMonthlyTotalCost(apartmentId, month);
                apartment.setTotalCost(totalCost);
                return apartmentRepository.save(apartment);

    }
    //Gán tiền dịch vụ của 1 loại dịch vụ cho 1 căn hộ
    public void ApartmentMonthlyServiceTypeCostAssignment(int apartmentId, int serviceTypeId, YearMonth month) {
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_TYPE_NOT_FOUND));
        Apartment apartment = apartmentService.getApartmentById(apartmentId);
        Optional<ServiceUsage> usageOptional = serviceUsageRepository
                .findByApartmentAndServiceTypeAndMonth(apartment, serviceType, month);

        usageOptional.ifPresent(usage -> {
            double totalCost = ApartmentMonthlyServiceTypeCost(apartmentId, serviceTypeId, month);
            usage.setTotal(totalCost);
            serviceUsageRepository.save(usage);
        });
    }

}
