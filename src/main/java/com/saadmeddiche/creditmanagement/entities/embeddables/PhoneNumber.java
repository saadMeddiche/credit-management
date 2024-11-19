package com.saadmeddiche.creditmanagement.entities.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Embeddable @Builder @ToString
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PhoneNumber implements Serializable {
    @Column(length = 50) @NotBlank
    private String number;

    @Column(length = 10) @NotBlank
    private String countryCode;
}
