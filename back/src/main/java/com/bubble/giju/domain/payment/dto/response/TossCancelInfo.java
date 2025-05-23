package com.bubble.giju.domain.payment.dto.response;


import lombok.Getter;


@Getter
public class TossCancelInfo {
    private String cancelReason;
    private String canceledAt;
    private int cancelAmount;
    private String receiptKey;
    private String transactionKey;
    private String cancelStatus;
}
