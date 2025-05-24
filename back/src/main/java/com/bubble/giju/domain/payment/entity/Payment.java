package com.bubble.giju.domain.payment.entity;

import com.bubble.giju.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private long id;

    @Column(name ="payment_key", nullable = true, length = 255)
    private String paymentKey;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;


    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "receipt_url")
    private String receiptUrl;

    @Column(name = "cash_receipt_url")
    private String cashReceiptUrl;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Builder
    public Payment(String paymentKey, int amount, String paymentMethod, String paymentStatus, String approvedAt, Order order, String receiptUrl, String cashReceiptUrl) {
        this.paymentKey = paymentKey;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.order = order;
        this.cashReceiptUrl = cashReceiptUrl;
        this.receiptUrl = receiptUrl;

        if (approvedAt != null && ("DONE".equals(paymentStatus) || "CANCELED".equals(paymentStatus) || "FAILED".equals(paymentStatus))) {
            OffsetDateTime odt = OffsetDateTime.parse(approvedAt);
            this.approvedAt = odt.toLocalDateTime();
        }
    }
    
}
