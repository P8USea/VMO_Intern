package com.example.apartmentmanagement.config;

import com.example.apartmentmanagement.entity.*;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ServiceTypeRepository;
import com.example.apartmentmanagement.repository.ServiceUsageRepository;
import com.example.apartmentmanagement.repository.UserRepository;
import com.example.apartmentmanagement.service.ApartmentCostService;
import com.example.apartmentmanagement.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class InitConfig {
    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;
    final ApartmentCostService apartmentCostService;
    final ApartmentRepository apartmentRepository;
    final ServiceTypeRepository serviceTypeRepository;
    final ServiceUsageRepository serviceUsageRepository;


    @Bean
    ApplicationRunner init(UserRepository userRepository, ApartmentService apartmentService) {
        List<Apartment> apartments = apartmentRepository.findAll();
        List<ServiceType> serviceTypes = serviceTypeRepository.findAll();
        YearMonth month = YearMonth.parse("2024-08");
        return args -> {
            if (!userRepository.existsByUsername("SuperAssmin")){
                HashSet<Role> roles = new HashSet<>();
                roles.add(Role.ASSMIN);
                User user = User.builder()
                        .username("SuperAssmin")
                        .password(passwordEncoder.encode("assmin"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.info("SuperAssmin has been created");

            }

             };
    }
}
