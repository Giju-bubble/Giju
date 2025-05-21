package com.bubble.giju.domain.cart.controller;


import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.request.CartSelectedRequestDto;
import com.bubble.giju.domain.cart.dto.request.DeleteCartRequestDto;
import com.bubble.giju.domain.cart.dto.request.UpdateQuantityRequestDto;
import com.bubble.giju.domain.cart.dto.response.CartItemResponseDto;
import com.bubble.giju.domain.cart.dto.response.CartResponseDto;
import com.bubble.giju.domain.cart.dto.response.CartListResponseDto;
import com.bubble.giju.domain.cart.service.CartService;
import com.bubble.giju.domain.user.dto.CustomPrincipal;
import com.bubble.giju.global.config.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "장바구니 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/cart")
@PreAuthorize("hasRole('USER')")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니에 상품 추가", description = "사용자가 선택한 상품을 장바구니에 추가합니다")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartResponseDto>> addItem(
            @AuthenticationPrincipal CustomPrincipal customPrincipal,
            @RequestBody @Valid AddToCartRequestDto addToCartRequestDto) {
        CartResponseDto addedCartItem =  cartService.addToCart(addToCartRequestDto, customPrincipal);
        ApiResponse<CartResponseDto> response = ApiResponse.success("장바구니에 상품이 추가되었습니다", addedCartItem);
        return ResponseEntity.ok(response);
    }

    /**
     * front에서 변동된 수량의 최종값을 받아 변동
    */
    @Operation(summary = "상품 수량 변동", description = "변동 된 값 만큼 업데이트 진행")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CartResponseDto>> updateQuantity(
            @AuthenticationPrincipal CustomPrincipal customPrincipal,
            @PathVariable Long id,
            @RequestBody @Valid UpdateQuantityRequestDto updateQuantityRequestDto) {

        CartResponseDto updateCount = cartService.updateQuantity(id, updateQuantityRequestDto, customPrincipal);
        ApiResponse<CartResponseDto> response = ApiResponse.success("상품의 수량이 변경되었습니다", updateCount);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장바구니 삭제", description = "선택된 장바구니 삭제합니다")
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteCartItem(
            @AuthenticationPrincipal CustomPrincipal customPrincipal,
            @RequestBody @Valid DeleteCartRequestDto deleteCartRequestDto) {
        cartService.deleteCartItem(deleteCartRequestDto.getCartIds(), customPrincipal);
        ApiResponse<String> response = ApiResponse.success("장바구니 항목 삭제 완료", null);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "장바구니 조회", description = "사용자가 물건을 담은 상품 장바구니 전체 조회합니다")
    @GetMapping
    public ResponseEntity<CartListResponseDto> getCartList( @AuthenticationPrincipal CustomPrincipal customPrincipal) {
        CartListResponseDto cartList = cartService.getCartList(customPrincipal);
        return ResponseEntity.ok(cartList);
    }

    @Operation(summary = "구매할 상품들 및 총값 조회", description = "구매하기 위해 선택된 각 상품의 정보(1개의 상품값, 상품값(상품*수량값), 갯수, 이름, 수량, cart아이디, 상품아이디), 선택된 상품 전체 총값, 배달비, 결제할 총값(총 상품값 + 배달비)")
    @GetMapping("/buy")
    public ResponseEntity<CartListResponseDto> seletedforBuyCartList(
            @AuthenticationPrincipal CustomPrincipal customPrincipal,
            @RequestBody CartSelectedRequestDto cartSelectedRequestDto) {
        CartListResponseDto buyCartList = cartService.getBuyCartList(cartSelectedRequestDto.getCartIds(), customPrincipal);
        return ResponseEntity.ok(buyCartList);
    }
}
