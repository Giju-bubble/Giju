package com.bubble.giju.domain.cart.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddToCartResponseDto {
    private CartItemResponseDto cartItem;
    private int cartTotalPrice;

    @Builder
    public AddToCartResponseDto(CartItemResponseDto cartItem, int cartTotalPrice) {
        this.cartItem = cartItem;
        this.cartTotalPrice = cartTotalPrice;
    }
}
