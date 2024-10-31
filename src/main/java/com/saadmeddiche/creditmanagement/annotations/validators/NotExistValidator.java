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

@Component
@RequiredArgsConstructor
public class NotExistValidator implements ConstraintValidator<NotExist,String> {

    private final EntityManager entityManager;

    private final DataRequestExtractor request;

    private Class<?> entity;

    private String fieldName;

    private String nameOfPathVariableContainingId;


    @Override
    public void initialize(NotExist constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.fieldName = constraintAnnotation.fieldName();
        this.nameOfPathVariableContainingId = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null) return true;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<?> root = criteriaQuery.from(entity);

        Predicate predicate = criteriaBuilder.equal(root.get(fieldName), value);

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

        return typedQuery.getSingleResult() == 0;
    }
}
