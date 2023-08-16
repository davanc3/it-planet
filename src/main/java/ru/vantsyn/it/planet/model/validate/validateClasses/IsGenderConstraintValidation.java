package ru.vantsyn.it.planet.model.validate.validateClasses;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.vantsyn.it.planet.model.validate.IsGender;

public class IsGenderConstraintValidation implements ConstraintValidator<IsGender, String> {
    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {
        return gender == null || gender.equals("MALE") || gender.equals("FEMALE") || gender.equals("OTHER");
    }
}
