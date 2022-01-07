package com.example.fleetmanagementsystem.mapper;

import com.example.fleetmanagementsystem.domain.CarMaintenance;
import com.example.fleetmanagementsystem.dto.CarMaintenanceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMaintenanceMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    CarMaintenance mapToCarMaintenance(CarMaintenanceDto carMaintenanceDto);

    @Mapping(target = "createdDate", expression = "java(carMaintenance.getCreatedDate())")
    CarMaintenanceDto mapToCarMaintenanceDto(CarMaintenance carMaintenance);
}