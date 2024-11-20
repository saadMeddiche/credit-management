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
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class NotExistValidator implements ConstraintValidator<NotExist,Object> {

    private final EntityManager entityManager;

    private final DataRequestExtractor request;

    private Class<?> entity;

    private String[] formFieldNames;

    private String[] entityFieldNames;

    private String nameOfPathVariableContainingId;


    @Override
    public void initialize(NotExist constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.formFieldNames = constraintAnnotation.formFieldNames();
        this.entityFieldNames = constraintAnnotation.entityFieldNames();
        this.nameOfPathVariableContainingId = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if(value == null) return true;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<?> root = criteriaQuery.from(entity);

        if(formFieldNames.length == 0) throw new IllegalArgumentException("The formFieldNames attribute must contain at least one field name");

        if(formFieldNames.length != entityFieldNames.length) entityFieldNames = formFieldNames;

        Predicate predicate;

        try {
            Field field = value.getClass().getDeclaredField(formFieldNames[0]);
            field.setAccessible(true);
            predicate = criteriaBuilder.equal(root.get(entityFieldNames[0]), field.get(value));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("The field " + formFieldNames[0] + " is not found in the request object");
        }

        for (int i = 1; i < formFieldNames.length; i++) {

            String formFieldName = formFieldNames[i];

            String entityFieldName = entityFieldNames.length > 0 ? entityFieldNames[i] : formFieldName;

            try {
                Field field = value.getClass().getDeclaredField(formFieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(value);

                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(entityFieldName), fieldValue));

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalArgumentException("The field " + formFieldName + " is not found in the request object");
            }
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

        context.buildConstraintViolationWithTemplate("Record with the same " + Arrays.stream(formFieldNames).reduce((s1, s2) -> s1 + " and " + s2) +" already exists")
                .addPropertyNode(Arrays.stream(formFieldNames).reduce((s1, s2) -> s1 + "&" + s2).orElse(""))
                .addConstraintViolation();

        return typedQuery.getSingleResult() == 0;
    }
}
