package ru.vantsyn.it.planet.model.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.vantsyn.it.planet.model.validate.validateClasses.DateFormatConstraintValidation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateFormatConstraintValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateFormat {
    String message() default "Дата должна быть в формате ISO-8601";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
