package com.example.fleetmanagementsystem.controller;

import com.example.fleetmanagementsystem.dto.CarConsumptionDto;
import com.example.fleetmanagementsystem.dto.CarDto;
import com.example.fleetmanagementsystem.dto.CarMaintenanceDto;
import com.example.fleetmanagementsystem.mapper.CarMaintenanceMapper;
import com.example.fleetmanagementsystem.mapper.CarMapper;
import com.example.fleetmanagementsystem.service.CarMaintenanceService;
import com.example.fleetmanagementsystem.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarMaintenanceMapper carMaintenanceMapper;

    @Autowired
    private CarMaintenanceService carMaintenanceService;

    @PostMapping("/car")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<CarDto> addCompanyCar(@Valid @RequestBody CarDto carDto, Principal principal, @AuthenticationPrincipal Jwt jwt) {
        Long companyId = Long.valueOf(jwt.getClaim("companyId"));
        var car = carMapper.mapToCar(carDto);
        car.setCreatedBy(principal.getName());

        var savedCar = carService.save(car, companyId);
        carMaintenanceService.registerCarMaintenance(null, carDto.getCurrentNumberOfKm(), true, principal.getName(), savedCar);
        return ResponseEntity.ok().body(carMapper.mapToCarDto(savedCar));
    }

    @PostMapping("/car/{carId}/maintenance")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<CarMaintenanceDto> registerCarMaintenance(@Valid @RequestBody CarMaintenanceDto carMaintenanceDto, @PathVariable Long carId, Principal principal, @AuthenticationPrincipal Jwt jwt) {
        Long companyId = Long.valueOf(jwt.getClaim("companyId"));
        var carMaintenance = carMaintenanceMapper.mapToCarMaintenance(carMaintenanceDto);
        carMaintenance.setChecked(false);
        carMaintenance.setCreatedBy(principal.getName());
        return ResponseEntity.ok().body(carMaintenanceMapper.mapToCarMaintenanceDto(carMaintenanceService.save(carMaintenance, companyId, carId)));
    }

    @GetMapping("/car/maintenance")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<Collection<CarMaintenanceDto>> filterCarMaintenancesByDate(@RequestParam(required = false) Long carId, @RequestParam(required = false) String beforeDate, @RequestParam(required = false) Boolean checked, @AuthenticationPrincipal Jwt jwt) {
        Long companyId = Long.valueOf(jwt.getClaim("companyId"));
        if (carId == null && beforeDate == null && checked == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specify at least one of the following: carId, beforeDate and checked.");
        }
        return ResponseEntity.ok().body(carMaintenanceService.filterByDate(carId, beforeDate, checked, companyId).stream().map(c -> carMaintenanceMapper.mapToCarMaintenanceDto(c)).collect(Collectors.toList()));
    }

    @GetMapping("/car/{carId}/consumption")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<Collection<CarConsumptionDto>> getCarConsumption(@PathVariable Long carId, @RequestParam(required = false) Long transportId, @AuthenticationPrincipal Jwt jwt) {
        Long companyId = Long.valueOf(jwt.getClaim("companyId"));
        return ResponseEntity.ok().body(carService.getCarConsumption(carId, transportId, companyId));
    }
}
