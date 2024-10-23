package com.saadmeddiche.creditmanagement.properties;

import org.springframework.context.annotation.Profile;

@Profile("local")
public interface SeederProperties {
    boolean isEnabled();
    boolean setEnabled(boolean enabled);
}
