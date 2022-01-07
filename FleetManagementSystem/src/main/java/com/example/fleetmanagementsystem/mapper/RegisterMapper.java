package com.example.fleetmanagementsystem.mapper;

import com.example.fleetmanagementsystem.domain.User;
import com.example.fleetmanagementsystem.dto.RegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RegisterMapper {
    @Mapping(target = "enabled", constant = "true")
    User mapToUser(RegisterDto registerDto);
}
