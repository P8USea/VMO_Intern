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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication Controller")
public class AuthenticationController {
    AuthenticationService authenticationService;
    ApartmentService apartmentService;
    ApartmentCostService apartmentCostService;
    ServiceTypeRepository serviceTypeRepository;
    @PostMapping("/log-in")
    @Operation(summary = "Authenticate user", description = "Give an exact token by server if authenticated")
    public APIResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return APIResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
    @Operation(summary = "Introspect input token", description = "Give a clue whether input token is valid")
    @PostMapping("/introspect")
    public APIResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return APIResponse.<IntrospectResponse>builder().result(result).build();

    }
    @Operation(summary = "refresh token", description = "Refresh token by the expiry time")
    @PostMapping("/refresh")
    APIResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return APIResponse.<AuthenticationResponse>builder().result(result).build();
    }
    @Operation(summary = "Log out", description = "Log out user, the given token will be invalid")
    @PostMapping("/log-out")
    APIResponse<Void> logout(@RequestBody LogOutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return APIResponse.<Void>builder().build();
    }


}
