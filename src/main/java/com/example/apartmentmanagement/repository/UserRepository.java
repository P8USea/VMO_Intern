package com.example.apartmentmanagement.repository;

import com.example.apartmentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.is_Deleted = false")
    List<User> findAllActiveUsers();

    // Tìm user không bị xóa theo id
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.is_Deleted = false")
    Optional<User> findById(@Param("id") Integer id);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.is_Deleted = false")
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}