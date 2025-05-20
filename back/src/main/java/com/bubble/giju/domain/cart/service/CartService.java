package com.bubble.giju.domain.cart.service;

import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.request.UpdateQuantityRequestDto;
import com.bubble.giju.domain.cart.dto.response.CartListResponseDto;
import com.bubble.giju.domain.cart.dto.response.CartResponseDto;

import java.util.List;


public interface CartService {
    CartResponseDto addToCart(AddToCartRequestDto requestDto);
    CartResponseDto updateQuantity(Long id, UpdateQuantityRequestDto updateQuantityRequestDto);
    void deleteCartItem(List<Long> cartIds);
    CartListResponseDto getCartList();
    CartListResponseDto getBuyCartList(List<Long> cartIds);
}
