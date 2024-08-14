package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.Resident;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class TestUtil {
    public static Resident createResidentUtil(){
        Resident resident = new Resident();
        resident.setId(1);
        resident.setName("Fuc");
        resident.setEmail("fuc@gmail.com");
        resident.setPhoneNumber("123456789");
        resident.setIdentityNumber("123");
        resident.setBirthYear(1990);
        resident.setGender("male");

        return resident;
    }
    public static Apartment createApartmentUtil() {
        Apartment apartment = new Apartment();
        apartment = new Apartment();
        apartment.setId(1);
        apartment.setArea(10);
        apartment.setRooms(3);
        apartment.setNumber(20);
        apartment.setResidents(List.of(createResidentUtil()));
        return apartment;

    }
}
