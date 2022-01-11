package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.domain.Car;
import com.example.fleetmanagementsystem.domain.TransportAction;
import com.example.fleetmanagementsystem.dto.CarConsumptionDto;
import com.example.fleetmanagementsystem.exception.CarNotFoundException;
import com.example.fleetmanagementsystem.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CompanyService companyService;
    private final TransportService transportService;

    @Autowired
    public CarService(CarRepository carRepository, CompanyService companyService, @Lazy TransportService transportService) {
        this.carRepository = carRepository;
        this.companyService = companyService;
        this.transportService = transportService;
    }

    private CarConsumptionDto getCarConsumptionForTransport(Long transportId, Long companyId, Car car) {
        var transportActions = transportService.getActions(transportId, companyId).stream().sorted(Comparator.comparing(TransportAction::getCreatedDate)).collect(Collectors.toList());

        Integer kmsDone = transportActions.get(transportActions.size() - 1).getNumberOfKm() - transportActions.get(0).getNumberOfKm();
        double fuelUsed = 0.0;
        for (int i = 0; i < transportActions.size() - 1; i++) {
            if (transportActions.get(i).getCurrentFuel() - transportActions.get(i + 1).getCurrentFuel() > 0) {
                fuelUsed += transportActions.get(i).getCurrentFuel() - transportActions.get(i + 1).getCurrentFuel();
            }
        }
        return new CarConsumptionDto(transportId, kmsDone, (long) (fuelUsed * car.getTankCapacity()));
    }

    public Car getCar(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car not found."));
    }

    public Car getCarWithCompany(Long id) {
        return carRepository.findOneWithCompany(id).orElseThrow(() -> new CarNotFoundException("Car not found."));
    }

    public Car save(Car car, Long companyId) {
        var company = companyService.getCompany(companyId);
        car.setCompany(company);
        carRepository.save(car);
        return getCar(car.getId());
    }

    public List<Car> getCompanyCars(Long companyId) {
        var company = companyService.getCompany(companyId);
        return carRepository.findAllByCompany(company);
    }


    public List<CarConsumptionDto> getCarConsumption(Long carId, Long transportId, Long companyId) {
        var car = getCarWithCompany(carId);
        if (!Objects.equals(car.getCompany().getId(), companyId)) {
            throw new CarNotFoundException("This car doesn't belong to your fleet.");
        }

        var carConsumptions = new ArrayList<CarConsumptionDto>();

        if (transportId != null) {
            carConsumptions.add(getCarConsumptionForTransport(transportId, companyId, car));
        } else {
            var transports = transportService.findAllByCar(car);
            for (var transport : transports) {
                carConsumptions.add(getCarConsumptionForTransport(transport.getId(), companyId, car));
            }
        }
        return carConsumptions;
    }
}
