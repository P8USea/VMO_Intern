package com.example.apartmentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResidentResponse {
    private int residentId;
    private int resident_UserId;
    private String residentName;
    private int resident_ApartmentId;

}
