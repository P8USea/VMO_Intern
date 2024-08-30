package com.example.apartmentmanagement.entity;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN, RESIDENT, MANAGER
    ;
    private String name;
    private String description;

}

