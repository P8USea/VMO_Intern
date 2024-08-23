package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.AuthenticationRequest;
import com.example.apartmentmanagement.dto.request.IntrospectRequest;
import com.example.apartmentmanagement.dto.request.LogOutRequest;
import com.example.apartmentmanagement.dto.request.RefreshRequest;
import com.example.apartmentmanagement.dto.response.AuthenticationResponse;
import com.example.apartmentmanagement.dto.response.IntrospectResponse;
import com.example.apartmentmanagement.dto.response.APIResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.ServiceType;
import com.example.apartmentmanagement.repository.ServiceTypeRepository;
import com.example.apartmentmanagement.service.ApartmentCostService;
import com.example.apartmentmanagement.service.ApartmentService;
import com.example.apartmentmanagement.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    ApartmentService apartmentService;
    ApartmentCostService apartmentCostService;
    ServiceTypeRepository serviceTypeRepository;
    @PostMapping("/log-in")
    public APIResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return APIResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
    @PostMapping("/introspect")
    public APIResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return APIResponse.<IntrospectResponse>builder().result(result).build();

    }
    @PostMapping("/refresh")
    APIResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return APIResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/log-out")
    APIResponse<Void> logout(@RequestBody LogOutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return APIResponse.<Void>builder().build();
    }


}
