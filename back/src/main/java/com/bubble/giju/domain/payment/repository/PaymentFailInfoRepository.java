package com.bubble.giju.domain.payment.repository;

import com.bubble.giju.domain.payment.entity.PaymentFailInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentFailInfoRepository extends JpaRepository<PaymentFailInfo, Long> {
}
