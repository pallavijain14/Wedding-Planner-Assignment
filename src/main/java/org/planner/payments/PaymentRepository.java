package org.planner.payments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Query("SELECT p FROM Payment p WHERE p.isCompleted = false")
    List<Payment> findPendingPayments();

    @Query("SELECT p FROM Payment p WHERE p.isCompleted = true")
    List<Payment> findCompletedPayments();

}
