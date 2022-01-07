package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.WebSecurityConfig;
import com.example.fleetmanagementsystem.domain.Authority;
import com.example.fleetmanagementsystem.domain.User;
import com.example.fleetmanagementsystem.dto.LoginDto;
import com.example.fleetmanagementsystem.dto.LoginResultDto;
import com.example.fleetmanagementsystem.dto.RegisterDto;
import com.example.fleetmanagementsystem.dto.UserDto;
import com.example.fleetmanagementsystem.exception.UserExistsException;
import com.example.fleetmanagementsystem.mapper.RegisterMapper;
import com.example.fleetmanagementsystem.mapper.UserMapper;
import com.example.fleetmanagementsystem.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private RegisterMapper registerMapper;

    @Autowired
    private UserMapper userMapper;

    public LoginResultDto loginUser(LoginDto loginDto) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(loginDto.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        if (passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
            Map<String, String> claims = new HashMap<>();

            String authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            claims.put(WebSecurityConfig.AUTHORITIES_CLAIM_NAME, authorities);

            return new LoginResultDto(jwtHelper.createJwtForClaims(loginDto.getUsername(), claims));
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }

    public UserDto registerUser(RegisterDto registerDto, Long companyId) {
        var company = companyService.getCompany(companyId);

        if (userService.getUser(registerDto.getUsername()) != null) {
            throw new UserExistsException("Username already exists.");
        }
        User user = registerMapper.mapToUser(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCompany(company);
        var userAuthorities = List.of(new Authority("ROLE_MEMBER", user));
        user.setAuthorities(userAuthorities);
        return userMapper.mapToUserDto(userService.save(user));
    }

}
