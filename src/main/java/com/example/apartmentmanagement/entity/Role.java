package com.example.apartmentmanagement.entity;

import lombok.Getter;

@Getter
public enum Role {
    ASSMIN, RESIDENT, MANAGER
    ;
    private String name;
    private String description;

}

