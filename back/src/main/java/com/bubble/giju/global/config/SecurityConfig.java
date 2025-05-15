package com.bubble.giju.global.config;

import com.bubble.giju.global.jwt.JWTFilter;
import com.bubble.giju.global.jwt.JWTUtil;
import com.bubble.giju.global.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(auth -> {
                    auth.disable();
                    System.out.println("Csrf enabled");
                })
                .formLogin(auth -> auth.disable())
                .httpBasic(auth -> auth.disable())
                // session 설정 -> stateless로 변경
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // h2 console
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

        // 경로별 인가 작업
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/**").permitAll()
//                              .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .anyRequest().authenticated()
                );

        // login filter 등록
        http
                .addFilterAt(new LoginFilter(authenticationConfiguration.getAuthenticationManager(), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}