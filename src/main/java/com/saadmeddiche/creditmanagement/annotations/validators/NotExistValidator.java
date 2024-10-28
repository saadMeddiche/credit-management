package com.saadmeddiche.creditmanagement.annotations.validators;

import com.saadmeddiche.creditmanagement.annotations.NotExist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotExistValidator implements ConstraintValidator<NotExist,String> {

    private final EntityManager entityManager;

    private Class<?> entity;

    private String fieldName;


    @Override
    public void initialize(NotExist constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null) return true;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<?> root = criteriaQuery.from(entity);

        Predicate predicate = criteriaBuilder.equal(root.get(fieldName), value);

        criteriaQuery.select(criteriaBuilder.count(root)).where(predicate);

        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getSingleResult() == 0;
    }
}
