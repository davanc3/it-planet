package ru.vantsyn.it.planet.model.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.vantsyn.it.planet.model.validate.validateClasses.CollectionValuesConstraintValidation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CollectionValuesConstraintValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCollectionValues {
    String message() default "Значения коллекции должны быть больше 0";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
