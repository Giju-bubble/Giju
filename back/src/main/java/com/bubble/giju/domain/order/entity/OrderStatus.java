package com.bubble.giju.domain.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    PENDING("주문대기"),
    SUCCEEDED("결제완료"),
    FAILED("결제실패"),
    CANCELED("주문취소"),
    PARTIALLY_CANCELED("주문 부분취소");


    private final String label;

}
