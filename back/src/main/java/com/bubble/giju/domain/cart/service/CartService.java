package com.bubble.giju.domain.cart.service;

import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.request.UpdateQuantityRequestDto;
import com.bubble.giju.domain.cart.dto.response.CartListResponseDto;
import com.bubble.giju.domain.cart.dto.response.CartResponseDto;
import com.bubble.giju.domain.user.dto.CustomPrincipal;

import java.util.List;


public interface CartService {
    CartResponseDto addToCart(AddToCartRequestDto requestDto, CustomPrincipal principal);
    CartResponseDto updateQuantity(Long id, UpdateQuantityRequestDto updateQuantityRequestDto, CustomPrincipal customPrincipal);
    void deleteCartItem(List<Long> cartIds, CustomPrincipal customPrincipal);
    CartListResponseDto getCartList(CustomPrincipal customPrincipal);
    CartListResponseDto getBuyCartList(List<Long> cartIds, CustomPrincipal customPrincipal);
}
