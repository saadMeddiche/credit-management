package com.saadmeddiche.creditmanagement.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable @Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PhoneNumber {
    @Column(length = 50)
    private @NotBlank String number;

    @Column(length = 10)
    private @NotBlank String countryCode;
}
