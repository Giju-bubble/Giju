package com.bubble.giju.domain.order.repository;


import com.bubble.giju.domain.order.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
