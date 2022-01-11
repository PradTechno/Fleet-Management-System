package com.example.fleetmanagementsystem.mapper;

import com.example.fleetmanagementsystem.domain.Transport;
import com.example.fleetmanagementsystem.dto.TransportDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface TransportMapper {
    @Mapping(target = "car.id", source = "carId")
    @Mapping(target = "user.username", expression = "java(transportDto.getDriver())")
    @Mapping(target = "startDate", source = "startDate")
    Transport mapToTransport(TransportDto transportDto);

    @Mapping(target = "driver", expression = "java(transport.getUser().getUsername())")
    @Mapping(target = "carId", expression = "java(transport.getCar().getId())")
    @Mapping(target = "status", expression = "java(transport.getStatus())")
    @Mapping(target = "id", expression = "java(transport.getId())")
    @Mapping(target = "finishDate", source = "finishDate")
    @Mapping(target = "estimatedDuration", expression = "java(transport.getRoute() != null ? transport.getRoute().getTotalDuration() : 0)")
    @Mapping(target = "estimatedDistance", expression = "java(transport.getRoute() != null ? transport.getRoute().getTotalDistance() : 0)")
    TransportDto mapToTransportDto(Transport transport);
}