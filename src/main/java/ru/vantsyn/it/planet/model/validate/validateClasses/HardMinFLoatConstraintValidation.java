package ru.vantsyn.it.planet.model.validate.validateClasses;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.vantsyn.it.planet.model.validate.HardMin;

public class HardMinFLoatConstraintValidation implements ConstraintValidator<HardMin, Float> {
    private float value;

    @Override
    public void initialize(HardMin constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Float value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value > this.value;
    }
}
