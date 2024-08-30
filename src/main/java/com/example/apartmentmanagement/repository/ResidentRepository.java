package com.example.apartmentmanagement.repository;

import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Integer> {
    List<Resident> findByApartmentId(Integer apartmentId);
}
