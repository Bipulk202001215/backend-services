package com.example.payment.api;

import com.example.payment.model.PaymentRequest;
import com.example.payment.model.PaymentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
        // TODO: integrate with real payment gateway / business logic
        PaymentResponse response = new PaymentResponse("PAYMENT-REF-123", "PENDING", request.amount());
        return ResponseEntity.ok(response);
    }
}

