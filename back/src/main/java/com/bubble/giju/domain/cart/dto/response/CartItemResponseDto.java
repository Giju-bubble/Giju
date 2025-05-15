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

    @Builder
    public CartItemResponseDto (Long cartId, Long drinkId, int quantity) {
        this.cartId = cartId;
        this.drinkId = drinkId;
        this.quantity = quantity;
    }
}
