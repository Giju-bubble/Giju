package com.bubble.giju.domain.order.service;

import com.bubble.giju.domain.order.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(String userId, List<Long> cartItemIds);
}
