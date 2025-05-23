package com.bubble.giju.domain.payment.repository;

import com.bubble.giju.domain.order.entity.Order;
import com.bubble.giju.domain.payment.entity.Payment;
import com.bubble.giju.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder(Order order);

    List<Payment> findAllByOrder_User(User user);
}