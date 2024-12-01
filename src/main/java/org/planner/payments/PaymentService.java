package org.planner.payments;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    public Payment recordPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> getPayments(Boolean pending) {
        if (pending != null) {
            return pending ? paymentRepository.findPendingPayments()
                    : paymentRepository.findCompletedPayments();
        }
        return paymentRepository.findAll();
    }
}
