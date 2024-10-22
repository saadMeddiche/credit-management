package com.saadmeddiche.creditmanagement.dto;

import com.saadmeddiche.creditmanagement.entity.embeddable.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public record PersonCreateRequest(
        @NotBlank @NotEmpty String firstName,
        @NotBlank @NotEmpty String lastName,
        @NotBlank @NotEmpty @Email String email,
        @NotBlank String job,
        @NotBlank String description,
        @Validated List<PhoneNumber> phoneNumbers
) {
}
