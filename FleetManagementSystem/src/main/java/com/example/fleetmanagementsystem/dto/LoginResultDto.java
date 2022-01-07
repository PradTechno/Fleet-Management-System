package com.example.fleetmanagementsystem.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginResultDto {
    @NonNull
    private String token;
}

