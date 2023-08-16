package ru.vantsyn.it.planet.model.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.vantsyn.it.planet.model.validate.validateClasses.IsLifeStatusConstraintValidation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsLifeStatusConstraintValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLifeStatus {
    String message() default "Значение должно быть равно ALIVE или DEAD";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
