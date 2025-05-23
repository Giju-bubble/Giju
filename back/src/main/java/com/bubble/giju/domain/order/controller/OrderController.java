package com.bubble.giju.domain.order.controller;

import com.bubble.giju.domain.order.dto.request.OrderRequestDto;
import com.bubble.giju.domain.order.dto.response.OrderResponseDto;
import com.bubble.giju.domain.order.service.OrderService;
import com.bubble.giju.domain.user.dto.CustomPrincipal;
import com.bubble.giju.global.config.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/order")
@PreAuthorize("hasRole('USER')")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(
            @RequestBody OrderRequestDto orderRequestDto,
            @AuthenticationPrincipal CustomPrincipal customPrincipal) {

        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto.getCartItemIds(), customPrincipal);
        ApiResponse<OrderResponseDto> response = ApiResponse.success("주문 추가 완료", orderResponseDto);
        return ResponseEntity.ok(response);
    }
}
