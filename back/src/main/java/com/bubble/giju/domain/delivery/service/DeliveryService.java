package com.bubble.giju.domain.delivery.service;

import com.bubble.giju.domain.delivery.dto.DeliveryCreateRequestDto;
import com.bubble.giju.domain.delivery.jaxb.DeliveryResponseDto;

public interface DeliveryService {
    DeliveryResponseDto  addDelivery(DeliveryCreateRequestDto deliveryCreateRequestDto);
}
