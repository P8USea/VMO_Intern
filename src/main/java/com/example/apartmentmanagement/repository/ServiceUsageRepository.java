package com.example.apartmentmanagement.repository;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.ServiceType;
import com.example.apartmentmanagement.entity.ServiceUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceUsageRepository extends JpaRepository<ServiceUsage, Integer> {

    List<List<ServiceUsage>> findByApartmentId(Integer apartmentId);

    @Query("SELECT u FROM ServiceUsage u WHERE u.month = :month")
    List<ServiceUsage> findByMonth(@Param("month") YearMonth month);

    @Query("SELECT u FROM ServiceUsage u WHERE u.apartment.id = :apartmentId AND u.month = :month")
    List<ServiceUsage> findByApartmentAndMonth(@Param("apartmentId") int apartmentId, @Param("month") YearMonth month);

    Optional<ServiceUsage> findByApartmentAndServiceTypeAndMonth(Apartment apartment, ServiceType serviceType, YearMonth month);
}
