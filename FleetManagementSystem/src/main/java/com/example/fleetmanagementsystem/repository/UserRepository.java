package com.example.fleetmanagementsystem.repository;

import com.example.fleetmanagementsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    Optional<User> getUserByUsername(String username);

    @Query("SELECT u FROM User u join u.authorities as a where u.company.id = :companyId and a.authority = :authority ")
    User findByCompany(long companyId, String authority);

}