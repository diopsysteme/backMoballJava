package org.example.backmobile.Validators;// Validator/ValidApprenantIds.java

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ApprenantIdsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidApprenantIds {
    String message() default "Invalid apprenant IDs";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
