package com.example.fleetmanagementsystem.dto;

import com.example.fleetmanagementsystem.validation.CarMaintenanceIsSet;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@CarMaintenanceIsSet
public class CarMaintenanceDto {
    private Long carId;
    @Future
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate setDate;
    @Min(1)
    private Long numberOfKm;

    private Boolean checked;

    private LocalDateTime createdDate;

    private String createdBy;
}
