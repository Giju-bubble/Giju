package com.bubble.giju.global.jwt;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    @Value("${jwt.expirationtime.refreshTime}")
    private int refreshTime;

    public Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(refreshTime);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // js가 접근 못하게 함
//        cookie.setSecure(true); // https 사용

        return cookie;
    }

    public Cookie deleteCookie(String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        return cookie;
    }
}
