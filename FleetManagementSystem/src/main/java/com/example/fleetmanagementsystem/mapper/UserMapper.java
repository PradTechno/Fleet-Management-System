package com.example.fleetmanagementsystem.mapper;

import com.example.fleetmanagementsystem.domain.User;
import com.example.fleetmanagementsystem.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public interface UserMapper {
    @Mapping(target = "authorities", expression = "java(user.getAuthorities().stream().map(x -> x.getAuthority()).collect(Collectors.toList()))")
    UserDto mapToUserDto(User user);
}