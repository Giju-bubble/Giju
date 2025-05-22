package com.bubble.giju.domain.order.service;

import com.bubble.giju.domain.order.entity.Order;
import com.bubble.giju.domain.user.dto.CustomPrincipal;
import com.bubble.giju.global.config.CustomException;

import java.util.List;

public interface OrderService {
    Order createOrder(List<Long> cartItemIds, CustomPrincipal customPrincipal);
}
