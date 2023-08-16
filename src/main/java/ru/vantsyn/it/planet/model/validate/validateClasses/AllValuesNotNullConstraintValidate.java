package ru.vantsyn.it.planet.model.validate.validateClasses;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.vantsyn.it.planet.model.validate.AllValuesNotNull;

import java.util.List;

public class AllValuesNotNullConstraintValidate implements ConstraintValidator<AllValuesNotNull, List<Long>> {
    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext constraintValidatorContext) {
        if (values == null) {
            return false;
        }
        for (Long value: values) {
            if (value == null) {
                return false;
            }
        }

        return true;
    }
}
