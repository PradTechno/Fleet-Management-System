package com.example.fleetmanagementsystem.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CarConsumptionDto {
    @NonNull
    private Long transportId;
    @NonNull
    private Integer kmsDone;
    @NonNull
    private Long literOfFuel;
}
