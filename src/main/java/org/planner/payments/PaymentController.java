package org.planner.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> recordPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.recordPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getPayments(@RequestParam(required = false) Boolean pending) {
        List<Payment> payments = paymentService.getPayments(pending);
        return ResponseEntity.ok(payments);
    }
}
