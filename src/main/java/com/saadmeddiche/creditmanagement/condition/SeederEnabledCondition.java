package com.saadmeddiche.creditmanagement.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Profile("local")
public abstract class SeederEnabledCondition implements Condition {

    protected abstract String key();

    @Override
    final public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty(key(), Boolean.class, false);
    }

}
