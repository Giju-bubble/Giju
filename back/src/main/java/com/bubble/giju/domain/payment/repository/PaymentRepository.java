package com.bubble.giju.domain.payment.repository;

import com.bubble.giju.domain.order.entity.Order;
import com.bubble.giju.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder(Order order);
}
