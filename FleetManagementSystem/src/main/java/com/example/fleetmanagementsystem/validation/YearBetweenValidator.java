package com.example.fleetmanagementsystem.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Year;

public class YearBetweenValidator implements
        ConstraintValidator<YearBetween, Integer> {

    private int minYear;

    @Override
    public void initialize(YearBetween contactNumber) {
        this.minYear = contactNumber.minYear();
    }

    @Override
    public boolean isValid(Integer yearField,
                           ConstraintValidatorContext cxt) {
        return yearField == null || (minYear <= yearField && yearField <= Year.now().getValue());
    }

}
