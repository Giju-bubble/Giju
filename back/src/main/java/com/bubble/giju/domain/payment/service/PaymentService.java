package com.bubble.giju.domain.payment.service;

public interface PaymentService {
    void paymentSuccess(String paymentKey, Long orderId, int amount);
}
