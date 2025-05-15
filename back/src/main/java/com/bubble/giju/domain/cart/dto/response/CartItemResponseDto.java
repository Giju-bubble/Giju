package com.bubble.giju.domain.cart.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemResponseDto {
    private Long cartId;
    private Long drinkId;
    private int quantity;
    private int unitPrice;
    private int totalPrice;

    @Builder
    public CartItemResponseDto (Long cartId, Long drinkId, int quantity, int unitPrice, int totalPrice) {
        this.cartId = cartId;
        this.drinkId = drinkId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
}
