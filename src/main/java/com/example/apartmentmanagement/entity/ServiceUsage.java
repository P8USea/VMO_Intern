package com.example.apartmentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Data
@Entity
@Component
@Table(name = "service_usage", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"apartment_id", "service_type_id", "month"})
})
public class ServiceUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment; // Căn hộ sử dụng dịch vụ

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;// Loại dịch vụ

    private double quantity;// Số lượng sử dụng

    @Column(nullable = false)
    private YearMonth month;

    private double total;
}