package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.response.APIResponse;
import com.example.apartmentmanagement.dto.response.ApartmentCostResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.ServiceUsage;
import com.example.apartmentmanagement.repository.ServiceTypeRepository;
import com.example.apartmentmanagement.repository.ServiceUsageRepository;
import com.example.apartmentmanagement.service.ApartmentCostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/apartments")
@RequiredArgsConstructor
@Tag(name = "Apartment Cost Controller")
public class ApartmentCostController {
    private static final Logger log = LoggerFactory.getLogger(ApartmentCostController.class);
    final ApartmentCostService apartmentCostService;
    final ServiceUsageRepository serviceUsageRepository;
    final ServiceTypeRepository serviceTypeRepository;
    @Operation(summary = "Give the cost of a service")
    @GetMapping("/{apartmentId}/cost/{serviceTypeId}")
    public APIResponse<Object> serviceTypeCost(
            @PathVariable int apartmentId,
            @PathVariable int serviceTypeId,
            @RequestParam("month") String month) {

        YearMonth yearMonth = YearMonth.parse(month);
        var result = apartmentCostService.ApartmentMonthlyServiceTypeCost(apartmentId, serviceTypeId, yearMonth);
        return APIResponse.builder()
                .result(result)
                .build();
    }

   /* @PostMapping("/{apartmentId}/total-cost")
    public ResponseEntity<Double> calculateAndSaveTotalCost(
            @PathVariable int apartmentId,
            @RequestParam("month") String month) {

        YearMonth yearMonth = YearMonth.parse(month);
        double totalCost = apartmentCostService.getServiceUsage(apartmentId, yearMonth);

        // Lưu kết quả vào database
        apartmentCostService.ApartmentMonthlyTotalCost(apartmentId, yearMonth);

        return ResponseEntity.ok(totalCost);
    }*/
    @Operation(summary = "Give the total cost of an apartment")
    @GetMapping("/{apartmentId}/total-cost")
    public APIResponse<ApartmentCostResponse> totalCost(
            @PathVariable int apartmentId,
            @RequestParam("month") String month) {

        YearMonth yearMonth = YearMonth.parse(month);
        var result = apartmentCostService.ApartmentMonthlyTotalCost(apartmentId, yearMonth);

        return APIResponse.<ApartmentCostResponse>builder()
                .result(result)
                .build();
    }
}
