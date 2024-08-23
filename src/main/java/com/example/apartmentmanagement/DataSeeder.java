package com.example.apartmentmanagement;

import com.example.apartmentmanagement.entity.*;
import com.example.apartmentmanagement.repository.*;
import com.example.apartmentmanagement.service.ApartmentCostService;
import com.example.apartmentmanagement.service.ApartmentService;
import com.example.apartmentmanagement.service.UserService;
import com.github.javafaker.Faker;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.List;
import java.util.Set;


/*
@Slf4j
@Component
public class DataSeeder implements CommandLineRunner {

    private final ResidentRepository residentRepository;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ApartmentService apartmentService;
    private final ServiceUsageRepository serviceUsageRepository;
    private final ApartmentCostService apartmentCostService;
    private final UserService userService;

    public DataSeeder(ResidentRepository residentRepository, UserRepository userRepository, ApartmentRepository apartmentRepository, ServiceTypeRepository serviceTypeRepository, ApartmentService apartmentService, ServiceUsageRepository serviceUsageRepository, ApartmentCostService apartmentCostService, UserService userService) {
        this.residentRepository = residentRepository;
        this.userRepository = userRepository;
        this.apartmentRepository = apartmentRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.apartmentService = apartmentService;
        this.serviceUsageRepository = serviceUsageRepository;
        this.apartmentCostService = apartmentCostService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        // Check if the data already exists
            Faker faker = new Faker();

            for (int i = 0; i < 10; i++) {  // Generate 10 fake residents
                User user = User.builder()
                        .roles(Set.of(Role.RESIDENT))
                        .username(faker.name().username())
                        .password(userService.hashPassGenerator(faker.internet().password()))
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .email(faker.internet().emailAddress())
                        .phone(faker.phoneNumber().phoneNumber())
                        .build();

                userRepository.save(user);


                Apartment apartment = Apartment.builder()
                        .area(36)
                        .rooms(36)
                        .capacity(3)
                        .build();
                apartmentRepository.save(apartment);

                ServiceType serviceType = ServiceType.builder()
                        .name(faker.commerce().productName())
                        .pricePerUnit(Double.parseDouble(faker.commerce().price()))
                        .build();
                serviceTypeRepository.save(serviceType);
                Resident resident = Resident.builder()
                        .apartment(apartment)
                        .user(user)
                        .build();
                residentRepository.save(resident);



            }
            List<Apartment> apartments = apartmentService.getAllApartments();
            log.info("All apartments found: " + apartments.size());
            List<ServiceType> serviceTypes = apartmentService.getServiceTypes();
            log.info("All serviceTypes found: " + serviceTypes.size());

            YearMonth month= YearMonth.parse("2024-08");

            List<ServiceUsage> serviceUsages = serviceUsageRepository.findAll();

            apartmentService.initializeServiceUsageForApartments(apartments,serviceTypes,month);

            for(Apartment apartment : apartments) {
                for(ServiceType serviceType : serviceTypes) {
                    serviceUsageRepository.save(apartmentCostService.calculateSingleCost(
                            apartment.getId(),
                            serviceType.getId(),
                            YearMonth.parse("2024-08")).orElse(null));
                }
                log.info("Apartment: {}", apartment.getId());
                apartmentRepository.save(apartmentCostService.calculateTotalCost(
                        apartment.getId(),
                        YearMonth.parse("2024-08")
                ).orElse(null));
            }




            System.out.println("Seeding complete!");
    }
} */
