package com.bubble.giju.domain.payment.controller;

import com.bubble.giju.domain.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "토스페이먼츠 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/payment")
@PreAuthorize("hasRole('USER')")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/success")
    public ResponseEntity<String> PaymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam Long orderId,
            @RequestParam int amount) {
        paymentService.paymentSuccess(paymentKey, orderId, amount);
        return ResponseEntity.ok("결제 성공 처리 완료");
    }
}
