package ru.vantsyn.it.planet.model.validate.validateClasses;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.vantsyn.it.planet.model.validate.NotEmptyCollection;

import java.util.List;

public class NotEmptyCollectionConstraintValidation implements ConstraintValidator<NotEmptyCollection, List<Long>> {
    @Override
    public boolean isValid(List<Long> collection, ConstraintValidatorContext constraintValidatorContext) {
        return collection != null && !collection.isEmpty();
    }
}
