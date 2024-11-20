package com.saadmeddiche.creditmanagement.annotations;

import com.saadmeddiche.creditmanagement.annotations.validators.NotExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NotExits.class)
@Constraint(validatedBy = NotExistValidator.class)
public @interface NotExist {

    Class<?> entity();

    String[] formFieldNames();

    String[] entityFieldNames() default {};

    String id() default "id"; // The name of path variable that contains the id of the record

    String message() default "Record already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
