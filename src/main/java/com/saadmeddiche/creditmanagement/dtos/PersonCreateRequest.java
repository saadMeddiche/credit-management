package com.saadmeddiche.creditmanagement.dtos;

import com.saadmeddiche.creditmanagement.entities.embeddables.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PersonCreateRequest(
        @NotBlank @NotEmpty String firstName,
        @NotBlank @NotEmpty String lastName,
        @NotBlank @NotEmpty @Email String email,
        @NotBlank @NotEmpty String job,
        String description,
        @Valid List<PhoneNumber> phoneNumbers
) {
}
