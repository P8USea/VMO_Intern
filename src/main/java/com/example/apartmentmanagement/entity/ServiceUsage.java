package com.example.apartmentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Data
@Entity
@Component
public class ServiceUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Apartment apartment; // Căn hộ sử dụng dịch vụ

    @ManyToOne
    private ServiceType serviceType; // Loại dịch vụ

    private double quantity; // Số lượng sử dụng
    private YearMonth month;
    private double total;
}