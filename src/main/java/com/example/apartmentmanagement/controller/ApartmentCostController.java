package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.service.ApartmentCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentCostController {

    @Autowired
    private ApartmentCostService apartmentCostService;

    @GetMapping("/{apartmentId}/cost/{serviceTypeId}")
    public ResponseEntity<Double> calculateServiceCost(
            @PathVariable int apartmentId,
            @PathVariable int serviceTypeId,
            @RequestParam("month") String month) {

        YearMonth yearMonth = YearMonth.parse(month);
        double cost = apartmentCostService.ApartmentMonthlyServiceTypeCost(apartmentId, serviceTypeId, yearMonth);

        return ResponseEntity.ok(cost);
    }

    @PostMapping("/{apartmentId}/total-cost")
    public ResponseEntity<Double> calculateAndSaveTotalCost(
            @PathVariable int apartmentId,
            @RequestParam("month") String month) {

        YearMonth yearMonth = YearMonth.parse(month);
        double totalCost = apartmentCostService.ApartmentMonthlyTotalCost(apartmentId, yearMonth);

        // Lưu kết quả vào database
        apartmentCostService.ApartmentMonthlyTotalCostAssignment(apartmentId, yearMonth);

        return ResponseEntity.ok(totalCost);
    }

    @PostMapping("/{apartmentId}/service-cost/{serviceTypeId}")
    public ResponseEntity<Void> calculateAndSaveServiceCost(
            @PathVariable int apartmentId,
            @PathVariable int serviceTypeId,
            @RequestParam("month") String month) {

        YearMonth yearMonth = YearMonth.parse(month);

        apartmentCostService.ApartmentMonthlyServiceTypeCostAssignment(apartmentId, serviceTypeId, yearMonth);

        return ResponseEntity.ok().build();
    }
}
