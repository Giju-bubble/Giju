package com.bubble.giju.domain.order.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDto {


    private String orderId;
    private int amount;
    private String orderName;
    private String customerEmail;
    private String customerName;
    private String successUrl;
    private String failUrl;

    @Builder
    public OrderResponseDto(String orderId, int amount, String orderName, String customerEmail, String customerName, String successUrl, String failUrl) {
        this.orderId = orderId;
        this.amount = amount;
        this.orderName = orderName;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
    }
}
