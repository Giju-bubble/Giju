package com.bubble.giju.domain.cart.service;

import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.request.DeleteCartRequestDto;
import com.bubble.giju.domain.cart.dto.request.UpdateQuantityRequestDto;
import com.bubble.giju.domain.cart.dto.response.AddToCartResponseDto;
import com.bubble.giju.domain.cart.dto.response.CartItemResponseDto;

import java.util.List;


public interface CartService {
    AddToCartResponseDto addToCart(AddToCartRequestDto requestDto);
    AddToCartResponseDto updateQuantity(Long id, UpdateQuantityRequestDto updateQuantityRequestDto);
    void deleteCartItem(List<Long> cartIds);
}
