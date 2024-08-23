package com.example.apartmentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Data
@Entity
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "service_usage", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"apartment_id", "service_type_id", "month"})
})
public class ServiceUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    double quantity;// Số lượng sử dụng
    YearMonth month;
    double total;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", nullable = false)

    Apartment apartment; // Căn hộ sử dụng dịch vụ

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_type_id", nullable = false)
    ServiceType serviceType;// Loại dịch vụ
}