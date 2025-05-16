package com.bubble.giju.domain.cart.controller;


import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.request.DeleteCartRequestDto;
import com.bubble.giju.domain.cart.dto.request.UpdateQuantityRequestDto;
import com.bubble.giju.domain.cart.dto.response.AddToCartResponseDto;
import com.bubble.giju.domain.cart.dto.response.CartItemResponseDto;
import com.bubble.giju.domain.cart.entity.Cart;
import com.bubble.giju.domain.cart.service.CartService;
import com.bubble.giju.global.config.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "장바구니 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/cart")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니에 상품 추가", description = "사용자가 선택한 상품을 장바구니에 추가합니다")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<AddToCartResponseDto>> addItem(@RequestBody @Valid AddToCartRequestDto addToCartRequestDto) {
        AddToCartResponseDto addedCartItem =  cartService.addToCart(addToCartRequestDto);
        ApiResponse< AddToCartResponseDto> response = ApiResponse.success("장바구니에 상품이 추가되었습니다", HttpStatus.OK, addedCartItem);
        return ResponseEntity.ok(response);
    }

    /**
     * front에서 변동된 수량의 최종값을 받아 변동
    */
    @Operation(summary = "상품 수량 변동", description = "변동 된 값 만큼 업데이트 진행")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AddToCartResponseDto>> updateQuantity(
            @PathVariable Long id,
            @RequestBody @Valid UpdateQuantityRequestDto updateQuantityRequestDto) {

        AddToCartResponseDto updateCount = cartService.updateQuantity(id, updateQuantityRequestDto);
        ApiResponse<AddToCartResponseDto> response = ApiResponse.success("상품의 수량이 변경되었습니다", HttpStatus.OK, updateCount);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장바구니 삭제", description = "선택된 장바구니 삭제합니다")
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteCartItem(@PathVariable @Valid DeleteCartRequestDto deleteCartRequestDto) {
        cartService.deleteCartItem(deleteCartRequestDto.getCartIds());
        ApiResponse<String> response = ApiResponse.success("장바구니 항목 삭제 완료", HttpStatus.OK, null);
        return ResponseEntity.ok(response);
    }
}
