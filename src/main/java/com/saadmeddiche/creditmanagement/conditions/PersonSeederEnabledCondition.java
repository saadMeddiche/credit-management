package com.saadmeddiche.creditmanagement.conditions;

import org.springframework.context.annotation.Profile;

@Profile("local")
public class PersonSeederEnabledCondition extends SeederEnabledCondition {

    @Override
    protected String key() {
        return "person-seeder.enabled";
    }

}
