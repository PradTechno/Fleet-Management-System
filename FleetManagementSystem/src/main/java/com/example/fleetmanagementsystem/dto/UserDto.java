package com.example.fleetmanagementsystem.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Collection;

@Data
public class UserDto {
    @NonNull
    private String username;
    @NonNull
    private String telephoneNumber;
    @NonNull
    private Collection<String> authorities;
}
