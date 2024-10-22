package com.saadmeddiche.creditmanagement.property;

import org.springframework.context.annotation.Profile;

@Profile("local")
public interface SeederProperties {
    boolean isEnabled();
    boolean setEnabled(boolean enabled);
}
