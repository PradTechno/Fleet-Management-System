package com.example.fleetmanagementsystem.validation;

import com.example.fleetmanagementsystem.dto.CarMaintenanceDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarMaintenanceIsSetValidator
        implements ConstraintValidator<CarMaintenanceIsSet, Object> {

    @Override
    public void initialize(CarMaintenanceIsSet constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        CarMaintenanceDto carMaintenance = (CarMaintenanceDto) obj;
        return carMaintenance.getSetDate() != null || carMaintenance.getNumberOfKm() != null;
    }
}