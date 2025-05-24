package com.bubble.giju.domain.payment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentCancelResponseDto {
    private Long orderId;
    private String orderName;
    private int cancelAmount;
    private boolean isFullCancel;
    private String receiptUrl; // 생성: "https://merchants.tosspayments.com/web/receipt?receiptKey=" + receiptKey
    private LocalDateTime canceledAt;
    private List<CanceledItemResponseDto> canceledItems;

    @Builder
    public PaymentCancelResponseDto(Long orderId,String orderName, int cancelAmount, boolean isFullCancel, String receiptUrl, LocalDateTime canceledAt,List<CanceledItemResponseDto> canceledItems ) {
        this.orderId = orderId;
        this.orderName= orderName;
        this.cancelAmount = cancelAmount;
        this.isFullCancel = isFullCancel;
        this.receiptUrl = receiptUrl;
        this.canceledAt = canceledAt;
        this.canceledItems = canceledItems;
    }
}
