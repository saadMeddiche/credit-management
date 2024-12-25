package com.saadmeddiche.creditmanagement.annotations;

import com.saadmeddiche.creditmanagement.annotations.validators.ExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* This annotation is used to validate that the record exists in the database.
* */

@Target({ ElementType.FIELD , ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistValidator.class)
public @interface Exist {

    Class<?> entity();

    String entityField() default "id";

    String dtoField() default "id";

    String message() default "record does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
