package com.example.fleetmanagementsystem.dto;

import com.example.fleetmanagementsystem.domain.TransportStatusEnum;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class TransportDto {
    private Long id;
    @NotEmpty
    private String originAddress;
    @NotEmpty
    private String destinationAddress;
    @NotNull
    private String driver;
    @NotNull
    private Long carId;
    @Future
    @NotNull
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private TransportStatusEnum status;
    private Long estimatedDuration;
    private Long estimatedDistance;
}
