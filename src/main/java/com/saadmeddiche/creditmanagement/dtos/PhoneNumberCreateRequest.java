package com.saadmeddiche.creditmanagement.dtos;

import com.saadmeddiche.creditmanagement.annotations.Unique;
import com.saadmeddiche.creditmanagement.entities.PhoneNumber;
import jakarta.validation.constraints.NotBlank;

@Unique(entity = PhoneNumber.class, formFieldNames = {"number", "countryCode"})
public record PhoneNumberCreateRequest(@NotBlank String number,
                                       @NotBlank String countryCode) {
}
