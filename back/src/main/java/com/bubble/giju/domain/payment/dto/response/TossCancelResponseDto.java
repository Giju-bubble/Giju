package com.bubble.giju.domain.payment.dto.response;

import lombok.Getter;
import java.util.List;

@Getter
public class TossCancelResponseDto {
    private String paymentKey;
    private String orderId;
    private String status;
    private List<TossCancelInfo> cancels;
}
