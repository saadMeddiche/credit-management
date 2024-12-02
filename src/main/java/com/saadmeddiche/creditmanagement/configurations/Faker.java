package com.saadmeddiche.creditmanagement.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Faker {

    @Bean(name = "customFaker")
    public Faker customFaker() {
        return new Faker();
    }

}
