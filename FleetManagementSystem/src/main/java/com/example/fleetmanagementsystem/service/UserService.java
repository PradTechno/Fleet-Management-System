package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.domain.Authority;
import com.example.fleetmanagementsystem.domain.Company;
import com.example.fleetmanagementsystem.domain.User;
import com.example.fleetmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUser(String username) {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }


    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        userRepository.save(user);
        return getUser(user.getUsername());
    }

    public User getCompanyManager(Company company) {
        Authority authority = new Authority();
        authority.setAuthority("ROLE_MANAGER");
        return userRepository.findByCompany(company.getId(), authority.getAuthority());
    }
}
