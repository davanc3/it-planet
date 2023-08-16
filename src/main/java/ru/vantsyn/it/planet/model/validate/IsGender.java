package ru.vantsyn.it.planet.model.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.vantsyn.it.planet.model.validate.validateClasses.IsGenderConstraintValidation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsGenderConstraintValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsGender {
    String message() default "Значение должно быть равно MALE, FEMALE или OTHER";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
