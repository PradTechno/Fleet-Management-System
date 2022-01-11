package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.domain.Car;
import com.example.fleetmanagementsystem.domain.Company;
import com.example.fleetmanagementsystem.domain.LicenseCategoryEnum;
import com.example.fleetmanagementsystem.exception.CarNotFoundException;
import com.example.fleetmanagementsystem.repository.CarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private CompanyService companyService;
    @InjectMocks
    private CarService carService;

    @Test
    @DisplayName("Create car")
    void test_create_car() {
        var car = new Car();
        car.setId(1L);
        car.setBrand("MAN");
        car.setModel("TGA 18.430");
        car.setModelYear(2002);
        car.setTankCapacity(200);
        car.setLicenseNeeded(LicenseCategoryEnum.A);
        car.setCreatedBy("Username");

        var savedCar = new Car();
        savedCar.setId(1L);
        savedCar.setBrand("MAN");
        savedCar.setModel("TGA 18.430");
        savedCar.setModelYear(2002);
        savedCar.setTankCapacity(200);
        savedCar.setLicenseNeeded(LicenseCategoryEnum.A);
        savedCar.setCreatedBy("Username");

        var company = new Company();
        company.setId(1L);

        when(companyService.getCompany(1L)).thenReturn(company);
        when(carRepository.findById(1L)).thenReturn(Optional.of(savedCar));

        Car result = carService.save(car, 1L);

        assertThat(result).isNotNull();
        verify(companyService, times(1)).getCompany(1L);
        verify(carRepository, times(1)).save(car);
        verify(carRepository, times(1)).findById(savedCar.getId());
        verifyNoMoreInteractions(companyService, carRepository);
    }

    @Test
    @DisplayName("Get one car that exists after id")
    void test_getOne_WhenIdExists(){
        var savedCar = new Car();
        savedCar.setId(1L);
        savedCar.setBrand("MAN");
        savedCar.setModel("TGA 18.430");
        savedCar.setModelYear(2002);
        savedCar.setTankCapacity(200);
        savedCar.setLicenseNeeded(LicenseCategoryEnum.A);
        savedCar.setCreatedBy("Username");

        when(carRepository.findById(1L)).thenReturn(Optional.of(savedCar));
        Car car = carService.getCar(1L);

        assertThat(car).isEqualTo(savedCar);
        verify(carRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    @DisplayName("Get one car that doesn't exist after id")
    void test_getOne_WhenIdNotExists(){
        var savedCar = new Car();
        savedCar.setId(1L);
        savedCar.setBrand("MAN");
        savedCar.setModel("TGA 18.430");
        savedCar.setModelYear(2002);
        savedCar.setTankCapacity(200);
        savedCar.setLicenseNeeded(LicenseCategoryEnum.A);
        savedCar.setCreatedBy("Username");

        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> carService.getCar(1L));
        assertThat(exception).hasMessage("Car not found.");
    }
}
