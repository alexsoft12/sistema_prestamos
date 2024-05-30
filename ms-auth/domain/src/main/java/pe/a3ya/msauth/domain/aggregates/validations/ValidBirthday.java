package pe.a3ya.msauth.domain.aggregates.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pe.a3ya.msauth.domain.aggregates.validations.impl.BirthDayValidatorImplement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BirthDayValidatorImplement.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBirthday {
    String message() default "invalid birthday date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
