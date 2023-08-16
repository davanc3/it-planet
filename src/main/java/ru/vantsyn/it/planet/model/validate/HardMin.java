package ru.vantsyn.it.planet.model.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.vantsyn.it.planet.model.validate.validateClasses.HardMinConstraintValidation;
import ru.vantsyn.it.planet.model.validate.validateClasses.HardMinFLoatConstraintValidation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {HardMinConstraintValidation.class, HardMinFLoatConstraintValidation.class})
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface HardMin {
    int value();
    String message() default "Значение меньше либо равно указанного";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
