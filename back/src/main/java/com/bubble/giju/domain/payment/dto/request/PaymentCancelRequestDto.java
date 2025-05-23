package com.bubble.giju.domain.payment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentCancelRequestDto {
    private Long orderId;
    private List<CanceledItemDto> canceledItmes;
    private String cancelReason;

    @Builder
    public PaymentCancelRequestDto (Long orderId, List<CanceledItemDto> canceledItmes, String cancelReason) {
        this.orderId = orderId;
        this.canceledItmes = canceledItmes;
        this.cancelReason = cancelReason;
    }
}
