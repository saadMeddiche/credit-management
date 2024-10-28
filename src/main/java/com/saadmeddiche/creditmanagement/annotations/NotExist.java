package com.saadmeddiche.creditmanagement.annotations;

import com.saadmeddiche.creditmanagement.annotations.validators.NotExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotExistValidator.class)
public @interface NotExist {

    Class<?> entity();

    String fieldName();

    String message() default "Record already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
