package com.saadmeddiche.creditmanagement.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local")
@Getter @Setter @Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "person-seeder")
public class PersonSeederProperties implements SeederProperties {
    private boolean enabled;
    private Integer personCount;
    private Integer phoneNumberPerPerson;
    private Integer creditPerPerson;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean setEnabled(boolean enabled) {
        return this.enabled = enabled;
    }
}
