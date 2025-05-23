package com.bubble.giju.domain.payment.service.paymentImpl;

import com.bubble.giju.domain.cart.repository.CartRepository;
import com.bubble.giju.domain.order.entity.Order;
import com.bubble.giju.domain.order.entity.OrderDetail;
import com.bubble.giju.domain.order.entity.OrderStatus;
import com.bubble.giju.domain.order.repository.OrderRepository;
import com.bubble.giju.domain.payment.dto.response.TossPaymentResponseDto;
import com.bubble.giju.domain.payment.entity.Payment;
import com.bubble.giju.domain.payment.entity.PaymentCancelInfo;
import com.bubble.giju.domain.payment.entity.PaymentFailInfo;
import com.bubble.giju.domain.payment.repository.PaymentCancelInfoRepository;
import com.bubble.giju.domain.payment.repository.PaymentFailInfoRepository;
import com.bubble.giju.domain.payment.repository.PaymentRepository;
import com.bubble.giju.domain.payment.service.PaymentService;
import com.bubble.giju.domain.payment.tossClient.TossClientImpl.TossClientImpl;
import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final TossClientImpl tossClientImpl;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentCancelInfoRepository paymentCancelInfoRepository;
    private final PaymentFailInfoRepository paymentFailInfoRepository;

    private static final String CANCEL_REASON = "결제 정보 불일치로 인한 자동 취소";
    private final CartRepository cartRepository;

    @Transactional
    @Override
    public void paymentSuccess(String paymentKey, String orderId, int amount) {

        // TossPayment -> orderId 타입이 Long
        Long orderIdToss = Long.parseLong(orderId);

        // 결제 승인 요청
        TossPaymentResponseDto tossResponse = tossClientImpl.confirmPayment(paymentKey, orderId, amount);

        // Order 테이블에서 실제 주문 조회
        Order order = getOrder(orderIdToss);

        if (!orderId.equals(tossResponse.getOrderId()) ||
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
        orderRepository.save(order);



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

        //결제 성공시 장바구니 삭제
        cartRepository.deleteByUser(order.getUser());
    }


    @Transactional
    @Override
    public void paymentFail(String code, String message, String orderId) {

        Long orderIdToss = Long.parseLong(orderId);
        Order order = getOrder(orderIdToss);

        // 실패 상태의 Payment 생성
        Payment payment = Payment.builder()
                .paymentKey(null) // 실패시에 paymentKey 없음
                .amount(order.getTotalAmount())
                .paymentMethod("PENDING")
                .paymentStatus("FAILED")
                .order(order)
                .build();

        paymentRepository.save(payment);

        // 실패 정보 저장
        PaymentFailInfo failInfo = PaymentFailInfo.builder()
                .failCode(code)
                .failMessage(message)
                .payment(payment)
                .build();

        paymentFailInfoRepository.save(failInfo);

        // 주문 상태도 실패로 변경
        order.updateStatus(OrderStatus.FAILED);
        orderRepository.save(order);
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
