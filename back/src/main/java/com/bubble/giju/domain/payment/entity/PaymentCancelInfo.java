package com.bubble.giju.domain.payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment_cancel_info")
public class PaymentCancelInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cancel_id")
    private Long Id;

    @Column(name ="cancel_reason")
    private String cancelReason;

    @Column(name = "cancel_amount", nullable = false)
    private int cancelAmount;

    @Column(name = "canceled_at", nullable = false)
    private LocalDateTime canceledAt;

    @Column(name = "receipt_key")
    private String receiptKey;

    @Column(name = "transaction_key")
    private String transactionKey;

    @Column(name = "cancel_status")
    private String cancelStatus;

    @Column(name = "is_full_cancel")
    private boolean isFullCancel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false, referencedColumnName = "payment_id")
    private Payment payment;

    @Builder
    public PaymentCancelInfo(String cancelReason, int cancelAmount, LocalDateTime canceledAt,
                             String receiptKey, String transactionKey, String cancelStatus,
                             boolean isFullCancel, Payment payment) {
        this.cancelReason = cancelReason;
        this.cancelAmount = cancelAmount;
        this.canceledAt = canceledAt;
        this.receiptKey = receiptKey;
        this.transactionKey = transactionKey;
        this.cancelStatus = cancelStatus;
        this.isFullCancel = isFullCancel;
        this.payment = payment;
    }
}