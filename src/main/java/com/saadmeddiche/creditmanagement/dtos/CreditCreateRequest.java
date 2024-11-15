package com.saadmeddiche.creditmanagement.dtos;

import com.saadmeddiche.creditmanagement.annotations.DateComparison;
import com.saadmeddiche.creditmanagement.enums.CreditType;
import com.saadmeddiche.creditmanagement.enums.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@DateComparison(startField = "grantDate", endField = "refundDate")
public record CreditCreateRequest(
        @NotNull @Positive Double amount,
        @NotNull Currency currency,
        @NotNull CreditType creditType,
        @NotBlank @NotEmpty String reason,
        @NotNull LocalDateTime grantDate,
        LocalDateTime refundDate
) { }
