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
    Optional<List<List<ServiceUsage>>> findByMonth(@Param("month") YearMonth month);

    @Query("SELECT u FROM ServiceUsage u WHERE u.apartment.id = :apartmentId AND u.month = :month")
    Optional<List<ServiceUsage>> findByApartmentAndMonth(@Param("apartmentId") int apartmentId, @Param("month") YearMonth month);
    @Query("SELECT u FROM ServiceUsage u WHERE u.apartment.id = :apartmentId AND u.month = :month AND u.serviceType.id = :serviceTypeId")
    Optional<ServiceUsage> findByApartmentAndServiceTypeAndMonth(int apartmentId, int serviceTypeId, YearMonth month);

    boolean existsByApartmentIdAndServiceTypeIdAndMonth(int apartmentId, int serviceTypeId, YearMonth month);

}
