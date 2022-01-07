package com.example.fleetmanagementsystem.repository;

import com.example.fleetmanagementsystem.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Override
    Optional<Company> findById(Long aLong);
}