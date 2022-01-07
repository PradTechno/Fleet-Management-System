package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.domain.Company;
import com.example.fleetmanagementsystem.exception.CompanyNotFoundException;
import com.example.fleetmanagementsystem.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company getCompany(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company not found."));
    }
}
