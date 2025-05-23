package com.bubble.giju.domain.payment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class OrderDetailResponseDto {
    private String drinkName;
    private int price;
    private int quantity;
    private int totalPrice;
    private boolean canceled;

    @Builder
    public OrderDetailResponseDto(String drinkName, int price, int quantity, int totalPrice, Boolean canceled) {
        this.drinkName = drinkName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.canceled = canceled;
    }
}
