package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ResidentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResidentServiceTest extends TestUtil{

    @Mock
    private ResidentRepository residentRepository;
    @Mock
    private ApartmentRepository apartmentRepository;
    @InjectMocks
    private ResidentService residentService;

    Apartment  apartment;
    List<Resident> residents;
    int apartmentId;
    int residentId;

    @BeforeEach
    void setUp() {
        apartmentId = 1;
        residentId = 1;
        residents = List.of(createResidentUtil(), createResidentUtil());
        apartment = createApartmentUtil();
    }

    @Test
    void getResidentsByApartmentTest() {
        when(residentRepository.findByApartmentId(apartmentId)).thenReturn(residents);
        List<Resident> result = residentService.getResidentsByApartment(apartmentId);
        assertNotNull(result); //Not Null
        assertEquals(2, result.size()); // Size bằng nhau
        assertTrue(result.contains(createResidentUtil())); //Có chứa phần tử
        assertInstanceOf(Resident.class, result.get(0)); //Kiểu trả về
        verify(residentRepository, times(1)).findByApartmentId(apartmentId); //Phương thức được gọi đúng số lần
    }
    @Test
    void getResidentByApartmentId_ShouldThrowException_WhenIdDoesNotExist() {
        when(residentRepository.findByApartmentId(apartmentId)).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () -> residentService.getResidentsByApartment(apartmentId));
    }

    @Test
    void createResident_whenValidResident_thenReturnSavedResident() {
        Resident resident = new Resident();

        when(residentRepository.save(resident)).thenReturn(resident);

        Resident savedResident = residentService.createResident(resident);

        assertNotNull(savedResident);
        assertEquals(resident, savedResident);
        verify(residentRepository, times(1)).save(resident);
    }


    @Test
    void updateResident_whenResidentExists_thenUpdateAndReturnResident() {
        Resident existingResident = new Resident();
        Resident residentDetails = new Resident();
        residentDetails.setName("New Name");

        when(residentRepository.findById(residentId)).thenReturn(Optional.of(existingResident));
        when(residentRepository.save(existingResident)).thenReturn(existingResident);

        Resident updatedResident = residentService.updateResident(residentId, residentDetails);

        assertNotNull(updatedResident);
        assertEquals("New Name", updatedResident.getName());
        verify(residentRepository, times(1)).findById(residentId);
        verify(residentRepository, times(1)).save(existingResident);
    }

    @Test
    void updateResident_whenResidentNotFound_thenThrowEntityNotFoundException() {
        Resident residentDetails = new Resident();

        when(residentRepository.findById(residentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> residentService.updateResident(residentId, residentDetails));
        verify(residentRepository, times(1)).findById(residentId);
    }


    @Test
    void deleteResident_whenResidentExists_thenDeleteResident() {

        doNothing().when(residentRepository).deleteById(residentId);

        residentService.deleteResident(residentId);

        verify(residentRepository, times(1)).deleteById(residentId);
    }

}