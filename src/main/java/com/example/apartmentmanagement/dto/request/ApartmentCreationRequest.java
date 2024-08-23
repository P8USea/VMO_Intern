package com.example.apartmentmanagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentCreationRequest {
    int number;
    double area;
    int rooms;
    boolean available;
    int capacity;
}
