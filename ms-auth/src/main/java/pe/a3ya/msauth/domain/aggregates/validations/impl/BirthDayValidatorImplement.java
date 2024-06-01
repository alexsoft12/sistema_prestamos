package pe.a3ya.msauth.domain.aggregates.validations.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pe.a3ya.msauth.domain.aggregates.validations.ValidBirthday;

import java.time.LocalDate;

public class BirthDayValidatorImplement implements ConstraintValidator<ValidBirthday, LocalDate> {
    @Override
    public void initialize(ValidBirthday constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate birthday, ConstraintValidatorContext context) {
        if (birthday == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return !birthday.isAfter(today) && birthday.isAfter(today.minusYears(120));
    }
}
