package com.example.fleetmanagementsystem.mapper;

import com.example.fleetmanagementsystem.domain.Car;
import com.example.fleetmanagementsystem.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    Car mapToCar(CarDto carDto);

    @Mapping(target = "companyId", source = "company.id")
    CarDto mapToCarDto(Car car);
}
