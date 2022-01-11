package com.example.fleetmanagementsystem.dto;

import com.example.fleetmanagementsystem.domain.LicenseCategoryEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class CarDtoValidationTest {

    @Test
    @DisplayName("Test request when all fields are valid")
    void test_request_whenIsValid() {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var request = new CarDto();
        request.setBrand("MAN");
        request.setModel("TGA 18.430");
        request.setModelYear(2002);
        request.setTankCapacity(200);
        request.setLicenseNeeded(LicenseCategoryEnum.A);
        request.setCurrentNumberOfKm(200000);

        var result = validator.validate(request);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Test request when model year is invalid")
    void test_request_whenModelYearIsInvalid() {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var request = new CarDto();
        request.setBrand("MAN");
        request.setModel("TGA 18.430");
        request.setModelYear(1990);
        request.setTankCapacity(200);
        request.setLicenseNeeded(LicenseCategoryEnum.A);
        request.setCurrentNumberOfKm(200000);

        var result = validator.validate(request);


        assertThat(result).hasSize(1);
        assertThat(result).allMatch(x -> Objects.equals(String.valueOf(x.getPropertyPath()), "modelYear"));
    }

    @Test
    @DisplayName("Test request when tank capacity is invalid")
    void test_request_whenTankCapacityIsInvalid() {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var request = new CarDto();
        request.setBrand("MAN");
        request.setModel("TGA 18.430");
        request.setModelYear(2000);
        request.setTankCapacity(0);
        request.setLicenseNeeded(LicenseCategoryEnum.A);
        request.setCurrentNumberOfKm(200000);

        var result = validator.validate(request);


        assertThat(result).hasSize(1);
        assertThat(result).allMatch(x -> Objects.equals(String.valueOf(x.getPropertyPath()), "tankCapacity"));
    }

}
