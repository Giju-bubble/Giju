package com.bubble.giju.domain.payment.service;

import com.bubble.giju.domain.payment.dto.request.PaymentCancelRequestDto;
import com.bubble.giju.domain.payment.dto.response.PaymentCancelResponseDto;
import com.bubble.giju.domain.payment.dto.response.PaymentHistoryDto;
import com.bubble.giju.domain.user.dto.CustomPrincipal;

import java.util.List;

public interface PaymentService {
    void paymentSuccess(String paymentKey, String orderId, int amount);
    void paymentFail(String code, String message, String orderId);
    PaymentCancelResponseDto paymentCancel(PaymentCancelRequestDto paymentCancelRequestDto);
    List<PaymentHistoryDto> paymentHistory(CustomPrincipal principal);
}
