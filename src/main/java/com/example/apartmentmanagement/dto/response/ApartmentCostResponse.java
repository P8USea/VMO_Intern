package com.example.apartmentmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ApartmentCostResponse {
    int apartmentId;
    String serviceType;
    double serviceTypeCost;
    double apartmentCost;


}
