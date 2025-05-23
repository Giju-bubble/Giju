package com.bubble.giju.domain.cart.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartResponseDto {
    private CartItemResponseDto cartItem;
    private int cartTotalPrice;

    @Builder
    public CartResponseDto(CartItemResponseDto cartItem, int cartTotalPrice) {
        this.cartItem = cartItem;
        this.cartTotalPrice = cartTotalPrice;
    }
}
