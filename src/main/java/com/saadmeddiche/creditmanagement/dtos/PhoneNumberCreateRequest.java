package com.saadmeddiche.creditmanagement.dtos;

import com.saadmeddiche.creditmanagement.annotations.NotExist;
import com.saadmeddiche.creditmanagement.entities.PhoneNumber;
import jakarta.validation.constraints.NotBlank;

@NotExist(entity = PhoneNumber.class, formFieldNames = {"number", "countryCode"})
public record PhoneNumberCreateRequest(@NotBlank String number,
                                       @NotBlank String countryCode) {
}
