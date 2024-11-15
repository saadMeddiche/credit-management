package com.saadmeddiche.creditmanagement.annotations.validators;

import com.saadmeddiche.creditmanagement.annotations.DateComparison;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class DateComparisonValidator implements ConstraintValidator<DateComparison, Object> {

    private String startField;
    private String endField;
    private final Logger logger = Logger.getLogger(DateComparisonValidator.class.getName());

    @Override
    public void initialize(DateComparison constraintAnnotation) {
        this.startField = constraintAnnotation.startField();
        this.endField = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            Field startDateField = value.getClass().getDeclaredField(startField);
            Field endDateField = value.getClass().getDeclaredField(endField);

            startDateField.setAccessible(true);
            endDateField.setAccessible(true);

            Object startDateObj = startDateField.get(value);
            Object endDateObj = endDateField.get(value);

            if (startDateObj instanceof LocalDateTime startDate && endDateObj instanceof LocalDateTime endDate) {

                context.disableDefaultConstraintViolation();

                context.buildConstraintViolationWithTemplate(String.format("The %s must be before the %s", startField, endField))
                        .addPropertyNode(startField)
                        .addConstraintViolation();

                context.buildConstraintViolationWithTemplate(String.format("The %s must be after the %s", endField, startField))
                        .addPropertyNode(endField)
                        .addConstraintViolation();

                return startDate.isBefore(endDate);
            }else {
                logger.severe("Fields are not of type LocalDateTime");
                logger.info("Validation will be false");
                return false;
            }

        } catch (Exception e) {
            logger.severe("Error while comparing dates: " + e.getMessage());
            logger.info("Validation will be false");
            return false;
        }

    }
}

