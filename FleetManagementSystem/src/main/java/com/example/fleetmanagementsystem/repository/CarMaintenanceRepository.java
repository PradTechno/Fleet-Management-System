package com.example.fleetmanagementsystem.repository;

import com.example.fleetmanagementsystem.domain.Car;
import com.example.fleetmanagementsystem.domain.CarMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface CarMaintenanceRepository extends JpaRepository<CarMaintenance, Long> {
    List<CarMaintenance> findAllByCarAndCheckedAndSetDateIsLessThanEqual(Car car, Boolean checked, LocalDate beforeDate);

    List<CarMaintenance> findAllByCarAndSetDateIsLessThanEqual(Car car, LocalDate beforeDate);

    List<CarMaintenance> findAllByCarAndChecked(Car car, Boolean checked);

    List<CarMaintenance> findAllByCarInAndCheckedAndSetDateIsLessThanEqual(List<Car> cars, Boolean checked, LocalDate beforeDate);

    List<CarMaintenance> findAllByCarInAndSetDateIsLessThanEqual(List<Car> cars, LocalDate beforeDate);

    List<CarMaintenance> findAllByCarInAndChecked(List<Car> cars, Boolean checked);

    Collection<CarMaintenance> findAllByCar(Car car);
}