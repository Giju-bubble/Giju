package com.bubble.giju.domain.payment.service;

public interface PaymentService {
    void paymentSuccess(String paymentKey, String orderId, int amount);
    void paymentFail(String code, String message, String orderId);
}
