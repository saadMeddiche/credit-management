package com.saadmeddiche.creditmanagement.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PersonUpdateRequest(
        @NotBlank @NotEmpty String firstName,
        @NotBlank @NotEmpty String lastName,
        @NotBlank @NotEmpty @Email String email,
        @NotBlank @NotEmpty String job,
        String description,
        @Valid @NotNull List<PhoneNumberRequest> phoneNumberRequests
) {
}
