package com.bubble.giju.domain.cart.controller;


import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.response.AddToCartResponseDto;
import com.bubble.giju.domain.cart.dto.response.CartItemResponseDto;
import com.bubble.giju.domain.cart.entity.Cart;
import com.bubble.giju.domain.cart.service.CartService;
import com.bubble.giju.global.config.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "장바구니 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<AddToCartResponseDto>> addItem(@RequestBody @Valid AddToCartRequestDto addToCartRequestDto) {
        AddToCartResponseDto addedCartItem =  cartService.addToCart(addToCartRequestDto);
        ApiResponse< AddToCartResponseDto> response = ApiResponse.success("장바구니에 상품이 추가되었습니다", HttpStatus.OK,addedCartItem);
        return ResponseEntity.ok(response);
    }

}
