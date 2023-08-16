package ru.vantsyn.it.planet.model.validate.validateClasses;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.vantsyn.it.planet.model.validate.HardMax;

public class HardMaxConstraintValidation implements ConstraintValidator<HardMax, Double> {
    private double value;

    @Override
    public void initialize(HardMax constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
        return aDouble != null && aDouble < value;
    }
}
