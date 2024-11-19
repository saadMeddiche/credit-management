package com.saadmeddiche.creditmanagement.dtos;

import jakarta.validation.constraints.NotBlank;

public record PhoneNumberCreateRequest(@NotBlank String number,
                                       @NotBlank String countryCode) {
}
