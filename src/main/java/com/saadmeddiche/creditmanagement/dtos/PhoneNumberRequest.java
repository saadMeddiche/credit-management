package com.saadmeddiche.creditmanagement.dtos;

import jakarta.validation.constraints.NotBlank;

public record PhoneNumberRequest(@NotBlank String number,
                                 @NotBlank String countryCode,
                                 boolean add) { // true = should be added, false = should be removed
}
