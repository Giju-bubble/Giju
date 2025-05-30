package com.bubble.giju.domain.delivery.controller;

import com.bubble.giju.domain.delivery.dto.DeliveryCreateRequestDto;
import com.bubble.giju.domain.delivery.jaxb.DeliveryResponseDto;
import com.bubble.giju.domain.delivery.service.DeliveryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


//todo 테스트용 API. 메서드를 주문에서 사용해야할듯
@RestController
@Tag(name = "배달 API")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/api/delivery")
    public ResponseEntity<DeliveryResponseDto> addDelivery(@RequestBody DeliveryCreateRequestDto deliveryCreateRequestDto)
    {
        DeliveryResponseDto s =deliveryService.addDelivery(deliveryCreateRequestDto);
        return ResponseEntity.ok(s);
    }
}
