package com.example.payment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotBlank(message = "Account identifier is required")
        String accountId,

        @NotNull(message = "Amount is required")
        @Min(value = 1, message = "Amount must be greater than zero")
        Long amount
) {
}

