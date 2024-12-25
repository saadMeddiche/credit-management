package com.saadmeddiche.creditmanagement.annotations.validators;

import com.saadmeddiche.creditmanagement.annotations.Exist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component @Slf4j
@RequiredArgsConstructor
public class ExistValidator implements ConstraintValidator<Exist,Object> {

    private final EntityManager entityManager;

    private Class<?> entity;

    private String entityField;

    private String dtoField;

    @Override
    public void initialize(Exist constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.entityField = constraintAnnotation.entityField();
        this.dtoField = constraintAnnotation.dtoField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {

        if(object == null) return true;

        Object value;
        try {
            value = extractValue(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Error while extracting value from object", e);
            return false;
        }

        buildFailMessage(context, value);

        CriteriaQuery<Long> criteriaQuery = buildQuery(value);

        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getSingleResult() > 0;
    }

    private Object extractValue(Object object) throws NoSuchFieldException, IllegalAccessException {

        Field field = object.getClass().getDeclaredField(this.dtoField);

        field.setAccessible(true);

        return field.get(object);

    }

    private CriteriaQuery<Long> buildQuery(Object value) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<?> root = criteriaQuery.from(this.entity);

        Predicate predicate = criteriaBuilder.equal(root.get(this.entityField), value);

        return criteriaQuery.select(criteriaBuilder.count(root)).where(predicate);

    }

    private void buildFailMessage(ConstraintValidatorContext context, Object value) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.format("There is no %s with %s='%s'", entity.getSimpleName(), dtoField, value))
                .addPropertyNode(this.dtoField)
                .addConstraintViolation();
    }

}
