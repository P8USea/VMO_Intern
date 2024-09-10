package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.ApartmentCreationRequest;
import com.example.apartmentmanagement.dto.response.APIResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.service.ApartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApartmentController.class) // Chỉ khởi tạo ApartmentController để test
@WithMockUser(username = "SuperAdmin", authorities = {"SCOPE_ADMIN"})
class ApartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApartmentService apartmentService;

    @Autowired
    private ObjectMapper objectMapper; // Dùng để chuyển đổi object thành JSON string

    private Apartment apartment;
    private ApartmentCreationRequest creationRequest;

    @BeforeEach
    void setUp() {
        apartment = new Apartment();
        apartment.setId(1);
        apartment.setNumber("101");
        apartment.setRooms(3);

        creationRequest = new ApartmentCreationRequest();
        creationRequest.setNumber("101");
        creationRequest.setRooms(3);
    }

    @Test
    void testGetAllApartments() throws Exception {
        List<Apartment> apartments = Collections.singletonList(apartment);

        // Mock hành vi của service
        when(apartmentService.getAllApartments()).thenReturn(apartments);
        // Gửi GET request và kiểm tra kết quả
        mockMvc.perform(get("/api/apartments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value("101"))
                .andExpect(jsonPath("$[0].rooms").value(3));
    }

    @Test
    void testGetApartmentById() throws Exception {
        // Mock hành vi của service
        when(apartmentService.getApartmentById(1)).thenReturn(apartment);

        // Gửi GET request và kiểm tra kết quả
        mockMvc.perform(get("/api/apartments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("101"))
                .andExpect(jsonPath("$.rooms").value(3));
    }

    @Test
    void testAddApartment() throws Exception {
        APIResponse<Object> response = APIResponse.builder().result(apartment).build();

        // Mock hành vi của service
        when(apartmentService.createApartment(any(ApartmentCreationRequest.class))).thenReturn(response);

        // Gửi POST request và kiểm tra kết quả
        mockMvc.perform(post("/api/apartments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.number").value("101"))
                .andExpect(jsonPath("$.result.rooms").value(3));
    }

    @Test
    void testUpdateApartment() throws Exception {
        Apartment updatedApartment = new Apartment();
        updatedApartment.setId(1);
        updatedApartment.setNumber("102");
        updatedApartment.setRooms(4);

        // Mock hành vi của service
        when(apartmentService.updateApartment(Mockito.anyInt(), Mockito.any(Apartment.class))).thenReturn(updatedApartment);

        // Gửi PUT request và kiểm tra kết quả
        mockMvc.perform(put("/api/apartments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedApartment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("102"))
                .andExpect(jsonPath("$.rooms").value(4));
    }

    @Test
    void testDeleteApartment() throws Exception {
        // Không cần mock service vì phương thức không trả về giá trị

        // Gửi DELETE request và kiểm tra kết quả
        mockMvc.perform(delete("/api/apartments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testSetProxy() throws Exception {
        // Mock hành vi của service
        Apartment updatedApartment = new Apartment();
        updatedApartment.setProxyId(2);
        when(apartmentService.getApartmentById(1)).thenReturn(updatedApartment);

        // Gửi PUT request để set proxy và kiểm tra kết quả
        mockMvc.perform(put("/api/apartments")
                        .param("apartmentId", "1")
                        .param("proxyId", "2"))
                .andExpect(status().isOk());

        // Kiểm tra xem proxy đã được set chính xác chưa
        Mockito.verify(apartmentService).getApartmentById(1);
        Mockito.verify(apartmentService.getApartmentById(1)).setProxyId(2);
    }
}
