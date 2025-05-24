package com.bubble.giju.domain.delivery.service;

import com.bubble.giju.domain.delivery.dto.DeliveryCompanyResponseDto;

import java.util.List;

public interface DeliveryCompanyService {
    List<DeliveryCompanyResponseDto> findAll();
    DeliveryCompanyResponseDto deleteById(int deliveryCompanyId);
}
