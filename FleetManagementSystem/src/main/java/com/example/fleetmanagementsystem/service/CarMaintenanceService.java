package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.domain.Car;
import com.example.fleetmanagementsystem.domain.CarMaintenance;
import com.example.fleetmanagementsystem.exception.CarMaintenanceNotFoundException;
import com.example.fleetmanagementsystem.exception.CarNotFoundException;
import com.example.fleetmanagementsystem.repository.CarMaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class CarMaintenanceService {
    @Autowired
    private CarMaintenanceRepository carMaintenanceRepository;

    @Autowired
    private CarService carService;

    public CarMaintenance getCarMaintenance(Long id) {
        return carMaintenanceRepository.findById(id).orElseThrow(() -> new CarMaintenanceNotFoundException("Car maintenance not found."));
    }

    public CarMaintenance save(CarMaintenance carMaintenance, Long carId, Long companyId) {
        var car = carService.getCar(carId);

        if(!Objects.equals(car.getCompany().getId(), companyId)){
            throw new CarNotFoundException("This car doesn't belong to your fleet.");
        }

        carMaintenance.setCar(car);

        carMaintenanceRepository.save(carMaintenance);
        return getCarMaintenance(carMaintenance.getId());
    }

    private void saveCarMaintenance(CarMaintenance carMaintenance){
        carMaintenanceRepository.save(carMaintenance);
    }

    public void registerCarMaintenance(LocalDate setDate, Integer numberOfKm, Boolean checked, String createdBy, Car car){

        var carMaintenance = new CarMaintenance();
        carMaintenance.setCar(car);
        carMaintenance.setSetDate(setDate);
        carMaintenance.setNumberOfKm(numberOfKm);
        carMaintenance.setCreatedDate(LocalDateTime.now());
        carMaintenance.setCreatedBy(createdBy);
        carMaintenance.setChecked(checked);
        saveCarMaintenance(carMaintenance);
    }
}
