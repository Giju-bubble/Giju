package com.bubble.giju.domain.payment.repository;

import com.bubble.giju.domain.payment.entity.PaymentCancelInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCancelInfoRepository extends JpaRepository<PaymentCancelInfo, Long> {
}
