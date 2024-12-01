package org.planner.Wedding;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.planner.clients.Client;
import org.planner.payments.Payment;
import org.planner.payments.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        // Create a test client
        Client client1 = new Client();
        client1.setName("Test Client 1");

        Client client2 = new Client();
        client2.setName("Test Client 2");

        // Add some test payments
        Payment completedPayment1 = new Payment();
        completedPayment1.setClient(client1);
        completedPayment1.setAmount(new BigDecimal("200.00"));
        completedPayment1.setIsCompleted(true);

        Payment completedPayment2 = new Payment();
        completedPayment2.setClient(client2);
        completedPayment2.setAmount(new BigDecimal("150.00"));
        completedPayment2.setIsCompleted(true);

        Payment pendingPayment1 = new Payment();
        pendingPayment1.setClient(client1);
        pendingPayment1.setAmount(new BigDecimal("300.00"));
        pendingPayment1.setIsCompleted(false);

        Payment pendingPayment2 = new Payment();
        pendingPayment2.setClient(client2);
        pendingPayment2.setAmount(new BigDecimal("100.00"));
        pendingPayment2.setIsCompleted(false);

        // Save payments
        paymentRepository.save(completedPayment1);
        paymentRepository.save(completedPayment2);
        paymentRepository.save(pendingPayment1);
        paymentRepository.save(pendingPayment2);
    }

    @Test
    void testFindPendingPaymentsReturnsAllPending() {
        List<Payment> pendingPayments = paymentRepository.findPendingPayments();
        assertEquals(2, pendingPayments.size(), "Should return 2 pending payments");
    }

    @Test
    void testFindPendingPaymentsReturnsEmptyIfNonePending() {
        // Clear all pending payments
        List<Payment> pendingPayments = paymentRepository.findCompletedPayments();
        pendingPayments.forEach(payment -> {
            payment.setIsCompleted(true);
            paymentRepository.save(payment);
        });

        // Test again
        List<Payment> noPendingPayments = paymentRepository.findCompletedPayments();
        assertTrue(noPendingPayments.isEmpty(), "Should return an empty list if no pending payments exist");
    }
}
