package ru.vantsyn.it.planet.model.validate.validateClasses;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.vantsyn.it.planet.model.validate.IsLifeStatus;

public class IsLifeStatusConstraintValidation implements ConstraintValidator<IsLifeStatus, String> {
    @Override
    public boolean isValid(String lifeStatus, ConstraintValidatorContext constraintValidatorContext) {
        return lifeStatus == null || lifeStatus.equals("ALIVE") || lifeStatus.equals("DEAD");
    }
}
