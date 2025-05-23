package com.bubble.giju.domain.payment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentHistoryDto {
    private Long orderId;
    private String orderName;
    private int amount;
    private String paymentStatus;
    private LocalDateTime paidAt;

    @Builder
    public PaymentHistoryDto(Long orderId, String orderName, int amount, String paymentStatus, LocalDateTime paidAt) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paidAt = paidAt;
    }
}
