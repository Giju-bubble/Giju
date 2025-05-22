package com.bubble.giju.domain.payment.service.paymentImpl;

import com.bubble.giju.domain.order.entity.Order;
import com.bubble.giju.domain.order.entity.OrderStatus;
import com.bubble.giju.domain.order.repository.OrderRepository;
import com.bubble.giju.domain.payment.dto.response.TossPaymentResponseDto;
import com.bubble.giju.domain.payment.entity.Payment;
import com.bubble.giju.domain.payment.entity.PaymentCancelInfo;
import com.bubble.giju.domain.payment.repository.PaymentCancelInfoRepository;
import com.bubble.giju.domain.payment.repository.PaymentRepository;
import com.bubble.giju.domain.payment.service.PaymentService;
import com.bubble.giju.domain.payment.tossClient.TossClientImpl.TossClientImpl;
import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final TossClientImpl tossClientImpl;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentCancelInfoRepository paymentCancelInfoRepository;

    private static final String CANCEL_REASON = "결제 정보 불일치로 인한 자동 취소";


    public void paymentSuccess(String paymentKey, Long orderId, int amount) {

        // TossPayment -> orderId 타입이 String
        String orderIdToss = orderId.toString();

        // 결제 승인 요청
        TossPaymentResponseDto tossResponse = tossClientImpl.confirmPayment(paymentKey, orderIdToss, amount);

        // Order 테이블에서 실제 주문 조회
        Order order = getOrder(orderId);

        if (!orderIdToss.equals(tossResponse.getOrderId()) ||
                order.getTotalAmount() != tossResponse.getTotalAmount()) {

            // Toss 측에 환불 요청
            //paymentKey, cancelReason, cancelAmount
            tossClientImpl.cancelPayment(
                    tossResponse.getPaymentKey(),
                    CANCEL_REASON,
                    tossResponse.getTotalAmount()
            );

            Payment canceledPayment = saveCanceledPayment(tossResponse, order);
            paymentCancelInfoRepository.save(PaymentCancelInfo.builder()
                    .cancelReason(CANCEL_REASON)
                    .payment(canceledPayment)
                    .build());

            // 이후 예외 처리
            throw new CustomException(ErrorCode.INVALID_PAYMENT_VERIFICATION);
        }

        // Order주문 상태 변경
        order.updateStatus(OrderStatus.SUCCEEDED);

        Payment payment = Payment.builder()
                .paymentKey(tossResponse.getPaymentKey())
                .amount(tossResponse.getTotalAmount())
                .paymentMethod(tossResponse.getMethod())
                .paymentStatus(tossResponse.getStatus())
                .approvedAt(tossResponse.getApprovedAt() != null ? tossResponse.getApprovedAt().toString() : null)
                .order(order)
                .receiptUrl(tossResponse.getReceipt() != null ? tossResponse.getReceipt().getReceiptUrl() : null)
                .cashReceiptUrl(tossResponse.getCashReceipt() != null ? tossResponse.getCashReceipt().getCashReceiptUrl() : null)
                .build();

        paymentRepository.save(payment);
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.NON_EXISTENT_ORDER));
    }

    private Payment saveCanceledPayment(TossPaymentResponseDto response, Order order) {
        Payment payment = Payment.builder()
                .paymentKey(response.getPaymentKey())
                .amount(response.getTotalAmount())
                .paymentMethod(response.getMethod())
                .paymentStatus("CANCELED")
                .approvedAt(response.getApprovedAt() != null ? response.getApprovedAt().toString() : null)
                .order(order)
                .receiptUrl(response.getReceipt() != null ? response.getReceipt().getReceiptUrl() : null)
                .cashReceiptUrl(response.getCashReceipt() != null ? response.getCashReceipt().getCashReceiptUrl() : null)
                .build();
        return paymentRepository.save(payment);
    }

}
