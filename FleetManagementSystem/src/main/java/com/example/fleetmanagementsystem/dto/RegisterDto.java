package com.example.fleetmanagementsystem.dto;

import com.example.fleetmanagementsystem.validation.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class RegisterDto {
    @NotEmpty
    @Size(max = 50)
    @Email
    private String username;

    @NotEmpty
    @Size(max = 68)
    private String password;

    @NotEmpty
    private String matchingPassword;

    @Pattern(regexp="^07[0-9]{8}$",message="Telephone number should have 10 digits.")
    private String telephoneNumber;
}
