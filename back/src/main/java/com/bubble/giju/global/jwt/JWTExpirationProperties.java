package com.bubble.giju.global.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt.expirationtime")
@Setter
@Getter
public class JWTExpirationProperties {
    private Long accessTime;
    private Long refreshTime;
}
