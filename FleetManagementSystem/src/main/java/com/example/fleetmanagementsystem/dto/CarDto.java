package com.example.fleetmanagementsystem.dto;

import com.example.fleetmanagementsystem.domain.LicenseCategoryEnum;
import com.example.fleetmanagementsystem.validation.YearBetween;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CarDto {
    @NotEmpty
    private String brand;
    @NotEmpty
    private String model;
    @NotNull
    @YearBetween(minYear = 2000)
    private Integer modelYear;
    @NotNull
    @Min(1)
    private Integer tankCapacity;
    @NotNull
    private LicenseCategoryEnum licenseNeeded;
    @NotNull
    private Integer currentNumberOfKm;


    private String createdBy;
    private Date createdDate;
    private Integer companyId;
}
