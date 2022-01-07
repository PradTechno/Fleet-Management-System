package com.example.fleetmanagementsystem.repository;

import com.example.fleetmanagementsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}