package com.example.fleetmanagementsystem.dto;

import com.example.fleetmanagementsystem.domain.TransportActionTypeEnum;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class TransportActionDto {
    @NotNull
    private Long id;
    @NotNull
    private TransportActionTypeEnum type;
    @NotNull
    @Min(1)
    private Integer numberOfKm;
    @NotNull
    @Min(0)
    @Max(1)
    private Double currentFuel;
    private LocalDateTime createdDate;
}
