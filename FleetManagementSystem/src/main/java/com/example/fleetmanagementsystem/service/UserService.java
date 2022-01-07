package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.domain.User;
import com.example.fleetmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        userRepository.save(user);
        return getUser(user.getUsername());
    }
}
