package ru.vantsyn.it.planet.model.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.vantsyn.it.planet.model.validate.validateClasses.HardMaxConstraintValidation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HardMaxConstraintValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HardMax {
    int value();
    String message() default "Значение больше либо равно указанного";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
