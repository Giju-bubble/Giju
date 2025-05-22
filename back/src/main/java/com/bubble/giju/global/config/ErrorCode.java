package com.bubble.giju.global.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // msg, Http code(200)
    /**
     * USER
     */

    // 아이디
    DUPLICATE_USER_LoginId("이미 사용 중인 아이디입니다", HttpStatus.CONFLICT),
    INVALID_USERID("적절하지 않은 사용자 아이디입니다.",HttpStatus.BAD_REQUEST),

    // 이미 사용중인 이메일
    DUPLICATE_USER_EMAIL("이미 사용 중인 이메일입니다", HttpStatus.CONFLICT),

    // 존재하지않는 유저
    NON_EXISTENT_USER("존재하지 않는 유저입니다", HttpStatus.BAD_REQUEST),

    // 사용자 일치 불일치
    USER_MISMATCH("사용자가 일치하지 않습니다", HttpStatus.FORBIDDEN),

    //권한 없음
    USER_UNAUTHORIZED("사용자가 권한이없음",HttpStatus.UNAUTHORIZED),


    /**
     * CART
     * */
    NON_EXISTENT_DRINK("해당 술은 존재 하지않음",HttpStatus.BAD_REQUEST),
    NON_EXISTENT_CART("장바구니가 존재하지 않습니다", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY("유요하지 않은 수량입니다",HttpStatus.BAD_REQUEST),

    /**
     * ORDER
     * */
    NON_EXISTENT_ORDER("주문이 존재 하지않습니다",HttpStatus.BAD_REQUEST),

    /**
     * PAYMENT
     * */
    INVALID_ORDER_ID("Toss의 OrderId와 Order DB의 OrderId 불일치", HttpStatus.BAD_REQUEST),
    INVALID_AMOUNT("Toss의 amount와 Order DB의 총 값이 불일치", HttpStatus.BAD_REQUEST),
    PAYMENT_CONFIRMATION_FAILED("결제 실패", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_VERIFICATION("결제 검증 실패", HttpStatus.BAD_REQUEST),


    /**
     * Auth
     */
    // Json파싱 실패
    INVALID_LOGIN_JSON("적절하지 않은 로그인 요청입니다", HttpStatus.BAD_REQUEST),
    // 로그인 실패
    LOGIN_UNAUTHORIZED("로그인 실패", HttpStatus.UNAUTHORIZED),

    /**
     * JWT
     */
    EXPIRED_JWT("JWT의 유효기간이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_refresh("refresh token이 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_access("access token이 없습니다.", HttpStatus.BAD_REQUEST),

    /**
     * Category
     */
    EXISTENT_CATEGORY("이미 존재하는 카테고리입니다.",HttpStatus.CONFLICT),
    NONEXISTENT_CATEGORY("존재하지 않는 카테고리입니다.",HttpStatus.BAD_REQUEST),

    /**
     * util
     */
    INVALID_IMAGE_FORMAT("잘못된 이미지 포맷입니다.", HttpStatus.BAD_REQUEST),

    /**
     * Error
     */
    INTERNAL_SERVER_ERROR("서버에러입니다. 백엔드 로그를 확인해주세요", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String msg;
    private final HttpStatus status;

    ErrorCode(String msg, HttpStatus status) {
        this.msg = msg;
        this.status = status;
    }
}
