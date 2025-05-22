package com.bubble.giju.domain.payment.tossClient;

import com.bubble.giju.domain.payment.dto.response.TossPaymentResponseDto;

public interface TossClient {
    TossPaymentResponseDto confirmPayment(String paymentKey, String orderId, int amount);
    void cancelPayment(String paymentKey, String cancelReason, int cancelAmount);
}
