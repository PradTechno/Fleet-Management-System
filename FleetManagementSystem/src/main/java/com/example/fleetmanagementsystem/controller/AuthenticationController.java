package com.example.fleetmanagementsystem.controller;

import com.example.fleetmanagementsystem.dto.LoginDto;
import com.example.fleetmanagementsystem.dto.LoginResultDto;
import com.example.fleetmanagementsystem.dto.RegisterDto;
import com.example.fleetmanagementsystem.dto.UserDto;
import com.example.fleetmanagementsystem.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<LoginResultDto> login(@Valid @RequestBody LoginDto loginDto) {
        var loginResult = authenticationService.loginUser(loginDto);
        return ResponseEntity.ok().body(loginResult);
    }

    @PostMapping("/company/{companyId}/manager")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDto> addCompanyManager(@Valid @RequestBody RegisterDto registerDto, @PathVariable Long companyId) {
        var registerResult = authenticationService.registerUser(registerDto, companyId, "ROLE_MANAGER");
        return ResponseEntity.ok().body(registerResult);
    }

    @PostMapping("/company/driver")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<UserDto> addCompanyDriver(@Valid @RequestBody RegisterDto registerDto, @AuthenticationPrincipal Jwt jwt) {
        Long companyId = Long.valueOf(jwt.getClaim("companyId"));
        var registerResult = authenticationService.registerUser(registerDto, companyId, "ROLE_DRIVER");
        return ResponseEntity.ok().body(registerResult);
    }
}
