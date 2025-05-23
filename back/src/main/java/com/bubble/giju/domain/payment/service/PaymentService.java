package com.bubble.giju.domain.payment.service;

import com.bubble.giju.domain.payment.dto.request.PaymentCancelRequestDto;
import com.bubble.giju.domain.payment.dto.response.PaymentCancelResponseDto;

public interface PaymentService {
    void paymentSuccess(String paymentKey, String orderId, int amount);
    void paymentFail(String code, String message, String orderId);
    PaymentCancelResponseDto paymentCancel(PaymentCancelRequestDto paymentCancelRequestDto);
}
