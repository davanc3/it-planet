package ru.vantsyn.it.planet.model.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.vantsyn.it.planet.model.validate.validateClasses.NotEmptyCollectionConstraintValidation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotEmptyCollectionConstraintValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyCollection {
    String message() default "Коллекция пустая";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
