package com.bubble.giju.domain.order.dto.request;

import com.bubble.giju.domain.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderRequestDto {
    private String id; // 추후  @AuthenticationPrincipa
    private List<Long> cartItemIds;

}
