package com.bubble.giju.domain.cart.service;

import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.response.CartItemResponseDto;


public interface CartService {
    CartItemResponseDto addToCart(AddToCartRequestDto requestDto);
}
