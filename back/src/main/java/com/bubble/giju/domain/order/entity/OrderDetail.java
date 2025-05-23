package com.bubble.giju.domain.order.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "drink_name", nullable = false)
    private String drinkName;

    @ManyToOne(fetch = FetchType.LAZY) //order 테이블
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @Builder
    public OrderDetail(String drinkName, int price, int quantity, Order order) {
        this.drinkName = drinkName;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }
}
