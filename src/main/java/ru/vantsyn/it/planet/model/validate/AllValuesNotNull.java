package ru.vantsyn.it.planet.model.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.vantsyn.it.planet.model.validate.validateClasses.AllValuesNotNullConstraintValidate;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AllValuesNotNullConstraintValidate.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AllValuesNotNull {
    String message() default "Значение в коллекции равно null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
