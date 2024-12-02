package org.example.backmobile.Validators;// Validator/ApprenantIdsValidator.java

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ApprenantIdsValidator implements ConstraintValidator<ValidApprenantIds, List<Long>> {

    @Override
    public boolean isValid(List<Long> apprenantIds, ConstraintValidatorContext context) {
        if (apprenantIds == null || apprenantIds.isEmpty()) {
            return false; // List must not be null or empty
        }

        // Check if all IDs are positive
        for (Long id : apprenantIds) {
            if (id == null || id <= 0) {
                return false; // Invalid ID found
            }
        }

        return true; // All IDs are valid
    }
}
