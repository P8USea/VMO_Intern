package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.repository.ApartmentRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceTest{
    @Mock
    public static ApartmentRepository apartmentRepository;

    @InjectMocks
    public static ApartmentService apartmentService;

    public static List<Apartment> apartments;


    static Apartment apartment1 = TestUtil.createApartmentUtil();
    static Apartment apartment2 = TestUtil.createApartmentUtil();

    Resident resident1 = TestUtil.createResidentUtil();
    Resident resident2 = TestUtil.createResidentUtil();
    static List<Apartment> MockApartments = Arrays.asList(apartment1, apartment2);


    @Test
    void getAllApartments() {

        when(apartmentRepository.findAll()).thenReturn(MockApartments);
        apartments = apartmentService.getAllApartments();

        assertAll(() -> assertInstanceOf(List.class, apartments) //Trả về dưới dạng list
                , () -> assertNotNull(apartments) //Not Null
                , () -> assertEquals(apartment1, apartments.get(0)) //Phần tử đầu tiên
                , () -> assertEquals(apartment2, apartments.get(1))); //Phần tử cuối cùng
    }

    @Test
    @DisplayName("By Id")
    void testGetApartmentById_ApartmentExists() {

        when(apartmentRepository.findById(apartment1.getId())).thenReturn(Optional.of(apartment1));

        Apartment result = apartmentService.getApartmentById(apartment1.getId());

        assertEquals(apartment1, result);
        verify(apartmentRepository).findById(apartment1.getId());
    }

    @Test
    void testGetApartmentById_ApartmentNotFound() {
        when(apartmentRepository.findById(apartment2.getId())).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> apartmentService.getApartmentById(apartment2.getId()),
                "Expected getApartmentById(2L) to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Apartment not found with id 1"));

        verify(apartmentRepository).findById(apartment2.getId());
    }
    @Test
    void createApartment_ApartmentExists() {

    }

    @Test
    void updateApartment() {
    }

    @Test
    void deleteApartment() {
    }
}