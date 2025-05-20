package com.bubble.giju.order.service;

import com.bubble.giju.domain.cart.entity.Cart;
import com.bubble.giju.domain.cart.repository.CartRepository;
import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.order.entity.Order;
import com.bubble.giju.domain.order.repository.OrderRepository;
import com.bubble.giju.domain.order.service.serviceImpl.OrderServiceImpl;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock private OrderRepository orderRepository;
    @Mock private UserRepository userRepository;
    @Mock private CartRepository cartRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User testUser;
    private Drink drink1, drink2;
    private Cart cart1, cart2;
    private final UUID testUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .userId(testUserId.toString())
                .loginId("test")
                .password("pass1234")
                .name("테스트 유저")
                .email("test@bubble.com")
                .birthday(LocalDate.of(2000,1,1))
                .phoneNumber("01012345678")
                .build();

        ReflectionTestUtils.setField(orderService, "deliveryCharge", 3000);

        drink1 = Drink.builder().id(1L).name("막걸리").price(15000).build();
        log.info("drink1: name={}, id={}, price={}", drink1.getName(), drink1.getId(), drink1.getPrice());

        drink2 = Drink.builder().id(2L).name("홍주").price(10000).build();
        log.info("drink2: name={}, id={}, price={}", drink2.getName(), drink2.getId(), drink2.getPrice());

    }

    @Test
    @DisplayName("주문이 정상적으로 생성, 물건값 30000원 이하 배달비 부과")
    void createdOrder() {
        // given
        log.info("[준비] userId = {}", testUserId);
        log.info("[준비] user = {}, userId = {}", testUser.getName(),testUser.getUserId());

        log.info("[준비] drink1 = {}, id = {}, price = {}", drink1.getName(), drink1.getId(), drink1.getPrice());
        log.info("[준비] drink2 = {}, id = {}, price = {}", drink2.getName(), drink2.getId(), drink2.getPrice());


        cart1 = Cart.builder().id(1L).user(testUser).drink(drink1).quantity(1).build(); // 15000
        cart2 = Cart.builder().id(2L).user(testUser).drink(drink2).quantity(1).build(); // 10000

        log.info("[준비] cart1 = drink: {}, quantity: {}", cart1.getDrink().getName(), cart1.getQuantity());
        log.info("[준비] cart2 = drink: {}, quantity: {}", cart2.getDrink().getName(), cart2.getQuantity());


        given(userRepository.findByLoginId("test")).willReturn(Optional.of(testUser));
        given(cartRepository.findAllById(List.of(1L, 2L))).willReturn(List.of(cart1, cart2));
        given(orderRepository.save(any(Order.class))).willAnswer(inv -> inv.getArgument(0));

        // when
        log.info("[실행] 주문 생성 로직 시작");
        Order result = orderService.createOrder(testUserId.toString(), List.of(1L, 2L));

        // then
        log.info("[검증] 생성된 Order: totalAmount = {}, deliveryCharge = {}, user = {}",
                result.getTotalAmount(), result.getDeliveryCharge(), result.getUser().getName());

        result.getOrderDetails().forEach(detail -> {
            log.info("[검증] OrderDetail - drinkName: {}, price: {}, quantity: {}, 주문참조 여부: {}",
                    detail.getDrinkName(), detail.getPrice(), detail.getQuantity(),
                    detail.getOrder() != null ? "true" : "fail");
        });

        assertThat(result.getTotalAmount()).isEqualTo(25000);
        assertThat(result.getDeliveryCharge()).isEqualTo(3000); // 3만원 미만 → 배달비 부과
        assertThat(result.getUser()).isEqualTo(testUser);
        assertThat(result.getOrderDetails()).hasSize(2);
        assertThat(result.getOrderDetails().get(0).getOrder()).isEqualTo(result); // 양방향 확인

        // verify
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(cartRepository).findAllById(List.of(1L, 2L));
    }

    @Test
    @DisplayName("주문이 정상적으로 생성, 물건값 30000원 이상 배달비 무료")
    void createdOrder2() {
        // given
        cart1 = Cart.builder().id(1L).user(testUser).drink(drink1).quantity(2).build(); // 30000
        log.info("Cart 생성: {}", cart1);
        cart2 = Cart.builder().id(2L).user(testUser).drink(drink2).quantity(2).build(); // 20000
        log.info("Cart 생성: {}", cart2);

        given(userRepository.findByLoginId("test")).willReturn(Optional.of(testUser));
        given(cartRepository.findAllById(List.of(1L, 2L))).willReturn(List.of(cart1, cart2));
        given(orderRepository.save(any(Order.class))).willAnswer(inv -> inv.getArgument(0));

        // when
        log.info("[실행] 주문 생성 로직 시작");
        Order result = orderService.createOrder(testUserId.toString(), List.of(1L, 2L));

        // then
        log.info("[검증] 생성된 Order: totalAmount = {}, deliveryCharge = {}, user = {}",
                result.getTotalAmount(), result.getDeliveryCharge(), result.getUser().getName());

        result.getOrderDetails().forEach(detail -> {
            log.info("[검증] OrderDetail - drinkName: {}, price: {}, quantity: {}, 주문참조 여부: {}",
                    detail.getDrinkName(), detail.getPrice(), detail.getQuantity(),
                    detail.getOrder() != null ? "true" : "fail");
        });

        assertThat(result.getTotalAmount()).isEqualTo(50000);
        assertThat(result.getDeliveryCharge()).isEqualTo(0); // 3만원 미만 → 배달비 부과
        assertThat(result.getUser()).isEqualTo(testUser);
        assertThat(result.getOrderDetails()).hasSize(2);
        assertThat(result.getOrderDetails().get(0).getOrder()).isEqualTo(result); // 양방향 확인

        // verify
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(cartRepository).findAllById(List.of(1L, 2L));
    }
}
