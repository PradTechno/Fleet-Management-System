package com.example.fleetmanagementsystem.validation;

import com.example.fleetmanagementsystem.dto.RegisterDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegisterDto user = (RegisterDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}