package com.saadmeddiche.creditmanagement.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "custom-static-resource")
public class CustomStaticResourceProperties {

    private String location;
}
