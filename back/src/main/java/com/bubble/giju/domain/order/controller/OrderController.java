package com.bubble.giju.domain.order.controller;

import com.bubble.giju.domain.order.dto.request.OrderRequestDto;
import com.bubble.giju.domain.order.dto.response.OrderResponse;
import com.bubble.giju.domain.order.entity.Order;
import com.bubble.giju.domain.order.service.OrderService;
import com.bubble.giju.global.config.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Order order = orderService.createOrder(orderRequestDto.getId(), orderRequestDto.getCartItemIds());
        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .build();

        ApiResponse<OrderResponse> response = ApiResponse.success("주문 추가 완료", HttpStatus.OK, orderResponse);
        return ResponseEntity.ok(response);
    }
}
