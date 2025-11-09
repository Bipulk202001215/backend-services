package com.example.payment.model;

public record PaymentResponse(String referenceId, String status, Long amount) {
}

