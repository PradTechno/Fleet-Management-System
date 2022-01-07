package com.example.fleetmanagementsystem.repository;

import com.example.fleetmanagementsystem.domain.CarMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarMaintenanceRepository extends JpaRepository<CarMaintenance, Long> {
}