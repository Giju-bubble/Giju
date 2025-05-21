package com.bubble.giju.domain.order.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private int totalAmount;

    @Builder
    public OrderResponse(Long id, int totalAmount) {
        this.id = id;
        this.totalAmount = totalAmount;
    }
}
