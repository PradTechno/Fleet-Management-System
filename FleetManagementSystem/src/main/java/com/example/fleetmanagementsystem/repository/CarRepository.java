package com.example.fleetmanagementsystem.repository;

import com.example.fleetmanagementsystem.domain.Car;
import com.example.fleetmanagementsystem.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByCompany(Company company);

    @Query(value = "SELECT c from Car c join fetch c.company where c.id = :id")
    Optional<Car> findOneWithCompany(Long id);
}