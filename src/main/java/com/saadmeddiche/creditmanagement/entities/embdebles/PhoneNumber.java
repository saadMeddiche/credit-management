package com.saadmeddiche.creditmanagement.entities.embdebles;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class PhoneNumber {
    private String number;
    private String countryCode;
}
