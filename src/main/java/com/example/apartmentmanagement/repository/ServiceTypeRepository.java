package com.example.apartmentmanagement.repository;

import com.example.apartmentmanagement.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {
    Optional<ServiceType> findByName(String name);
}
