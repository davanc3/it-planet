package ru.vantsyn.it.planet.model.validate.validateClasses;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.vantsyn.it.planet.model.validate.ValidCollectionValues;

import java.util.List;

public class CollectionValuesConstraintValidation implements ConstraintValidator<ValidCollectionValues, List<Long>> {
    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext constraintValidatorContext) {
        if (values == null) {
            return false;
        }
        for (long value: values) {
            if (value <= 0) {
                return false;
            }
        }

        return true;
    }
}
