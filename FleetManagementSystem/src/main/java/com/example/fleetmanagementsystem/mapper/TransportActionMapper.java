package com.example.fleetmanagementsystem.mapper;

import com.example.fleetmanagementsystem.domain.TransportAction;
import com.example.fleetmanagementsystem.dto.TransportActionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransportActionMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "transport.id", source = "id")
    @Mapping(target = "id", ignore = true)
    TransportAction mapToTransportAction(TransportActionDto transportActionDto);

    @Mapping(target = "createdDate", source = "createdDate")
    TransportActionDto mapToTransportActionDto(TransportAction transport);
}