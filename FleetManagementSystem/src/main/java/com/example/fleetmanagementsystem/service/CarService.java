package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.domain.Car;
import com.example.fleetmanagementsystem.exception.CarNotFoundException;
import com.example.fleetmanagementsystem.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CompanyService companyService;

    public Car getCar(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car not found."));
    }

    public Car save(Car car, Long companyId) {
        var company = companyService.getCompany(companyId);
        car.setCompany(company);
        carRepository.save(car);
        return getCar(car.getId());
    }
}
