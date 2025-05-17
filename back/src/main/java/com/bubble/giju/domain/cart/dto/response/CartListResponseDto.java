package com.bubble.giju.domain.cart.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
public class CartListResponseDto {
    private List<CartItemResponseDto> items;
    private int totalPrice;
}
