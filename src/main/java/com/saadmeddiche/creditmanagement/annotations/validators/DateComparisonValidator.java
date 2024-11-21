package com.saadmeddiche.creditmanagement.annotations.validators;

import com.saadmeddiche.creditmanagement.annotations.DateComparison;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class DateComparisonValidator implements ConstraintValidator<DateComparison, Object> {

    private String startField;
    private String endField;
    private final Logger logger = Logger.getLogger(DateComparisonValidator.class.getName());
    private final MessageSource messageSource;

    @Override
    public void initialize(DateComparison constraintAnnotation) {
        this.startField = constraintAnnotation.startField();
        this.endField = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return false;

        try {
            Field startDateField = value.getClass().getDeclaredField(startField);
            Field endDateField = value.getClass().getDeclaredField(endField);

            startDateField.setAccessible(true);
            endDateField.setAccessible(true);

            Object startDateObj = startDateField.get(value);
            Object endDateObj = endDateField.get(value);

            if (startDateObj instanceof LocalDateTime startDate && endDateObj instanceof LocalDateTime endDate) {

                context.disableDefaultConstraintViolation();

                String beforeMessage = messageSource.getMessage("validation.date-comparison.before", new Object[]{startField,endField}, LocaleContextHolder.getLocale());

                String afterMessage = messageSource.getMessage("validation.date-comparison.after", new Object[]{endField,startField}, LocaleContextHolder.getLocale());

                context.buildConstraintViolationWithTemplate(beforeMessage)
                        .addPropertyNode(startField)
                        .addConstraintViolation();

                context.buildConstraintViolationWithTemplate(afterMessage)
                        .addPropertyNode(endField)
                        .addConstraintViolation();

                return startDate.isBefore(endDate);
            }else {
                logger.severe("Fields are not of type LocalDateTime");
                logger.warning("Validation will be false");
                return false;
            }

        } catch (Exception e) {
            logger.severe("Error while comparing dates: " + e.getMessage());
            logger.warning("Validation will be false");
            return false;
        }

    }
}

