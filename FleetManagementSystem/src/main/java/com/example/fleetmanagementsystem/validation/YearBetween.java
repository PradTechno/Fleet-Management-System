package com.example.fleetmanagementsystem.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = YearBetweenValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface YearBetween {
    String message() default "Invalid year";

    int minYear() default 0;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
