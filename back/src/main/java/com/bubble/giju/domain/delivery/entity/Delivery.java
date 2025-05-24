package com.bubble.giju.domain.delivery.entity;

import com.bubble.giju.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Deliveries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delivery_company_id", nullable = false)
    private DeliveryCompany deliveryCompany;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @Column(name="delivery_num", nullable = false, unique = true)
    private String deliveryNum;
    @Builder
    public Delivery(DeliveryCompany deliveryCompany, Order order, String deliveryNum) {
        this.deliveryCompany = deliveryCompany;
        this.order = order;
        this.deliveryNum = deliveryNum;
    }
}
