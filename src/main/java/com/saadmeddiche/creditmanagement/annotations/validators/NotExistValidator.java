package com.saadmeddiche.creditmanagement.annotations.validators;

import com.saadmeddiche.creditmanagement.annotations.NotExist;
import com.saadmeddiche.creditmanagement.utils.DataRequestExtractor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@RequiredArgsConstructor
public class NotExistValidator implements ConstraintValidator<NotExist,Object> {

    private final EntityManager entityManager;

    private final DataRequestExtractor request;

    private final MessageSource messageSource;

    private Class<?> entity;

    private String[] formFieldNames;

    private String[] entityFieldNames;

    private String nameOfPathVariableContainingId;

    @Override
    public void initialize(NotExist constraintAnnotation) {

        // Extract data from the annotation
        this.entity = constraintAnnotation.entity();
        this.formFieldNames = constraintAnnotation.formFieldNames();
        this.entityFieldNames = constraintAnnotation.entityFieldNames();
        this.nameOfPathVariableContainingId = constraintAnnotation.id();

        // Why ? Because formFieldNames is a required attribute
        if(formFieldNames.length == 0) throw new IllegalArgumentException("The formFieldNames attribute must contain at least one field name");

        // If the entityFieldNames is not provided, we assume that the formFieldNames are the same as the entityFieldNames
        if(formFieldNames.length != entityFieldNames.length) entityFieldNames = formFieldNames;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if(value == null) return true;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<?> root = criteriaQuery.from(entity);

        // Create the first condition
        Predicate predicate = getPredicate(criteriaBuilder, root, value, formFieldNames[0], entityFieldNames[0]);

        // Create the other conditions
        for (int i = 1; i < formFieldNames.length; i++) {
            predicate = getPredicate(criteriaBuilder, root, predicate, value, formFieldNames[i], entityFieldNames[i]);
        }

        if(request.isHttpMethod(HttpMethod.PUT)) {

            // Extract the id from the path variables of the request
            String id = request.pathVariablesBuilder().get(nameOfPathVariableContainingId);

            if(id == null) { // If the id is not found in the path variables
                throw new IllegalArgumentException(String.format("The path variable %s is not found", nameOfPathVariableContainingId));
            }

            // Add a condition to exclude the current record from the search
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), id));
        }

        criteriaQuery.select(criteriaBuilder.count(root)).where(predicate);

        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);

        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate(messageSource.getMessage("validation.not-exist", new Object[]{entity.getSimpleName(),String.join(" , ", formFieldNames)}, LocaleContextHolder.getLocale()))
                .addPropertyNode(String.join("&", formFieldNames))
                .addConstraintViolation();

        return typedQuery.getSingleResult() == 0;
    }

    private Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Object value, String formFieldName, String entityFieldName) {
        try {
            Field field = value.getClass().getDeclaredField(formFieldName);
            field.setAccessible(true);
            return criteriaBuilder.equal(root.get(entityFieldName), field.get(value));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("The field " + formFieldName + " is not found in the request object");
        }
    }

    private Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Predicate predicate, Object value, String formFieldName, String entityFieldName) {
        try {
            Field field = value.getClass().getDeclaredField(formFieldName);
            field.setAccessible(true);
            return criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(entityFieldName), field.get(value)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("The field " + formFieldName + " is not found in the request object");
        }
    }
}
