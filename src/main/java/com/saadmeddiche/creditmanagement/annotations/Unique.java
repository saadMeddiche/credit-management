package com.saadmeddiche.creditmanagement.annotations;

import com.saadmeddiche.creditmanagement.annotations.validators.UniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Uniques.class)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {

    Class<?> entity();

    String[] formFieldNames();

    String[] entityFieldNames() default {};

    String id() default "id"; // The name of path variable that contains the id of the record

    String message() default "Record already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
