package ru.vantsyn.it.planet.model.validate.validateClasses;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.vantsyn.it.planet.model.validate.HardMin;

public class HardMinConstraintValidation implements ConstraintValidator<HardMin, Double> {
    private double value;

    @Override
    public void initialize(HardMin constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
        return aDouble != null && aDouble > value;
    }
}
