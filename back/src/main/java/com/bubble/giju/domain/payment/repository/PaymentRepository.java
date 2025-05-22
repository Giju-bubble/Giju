package com.bubble.giju.domain.payment.repository;

import com.bubble.giju.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
