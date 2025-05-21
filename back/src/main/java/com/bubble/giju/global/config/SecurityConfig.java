package com.bubble.giju.global.config;

import com.bubble.giju.global.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final CookieUtil cookieUtil;

    // CORS Configuration을 Bean으로 등록
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CORS 설정 적용 - Bean으로 등록한 corsConfigurationSource 사용
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

       // CSRF 비활성화 + H2 콘솔 허용
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                        .disable()
                );

        // 세션, 로그인 방식 비활성화
        http
                .formLogin(auth -> auth.disable())
                .httpBasic(auth -> auth.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

        // 경로별 인가 작업
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/**", "/error", "/h2-console/**").permitAll()
//                              .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .anyRequest().authenticated()
                );

        // login filter 등록
        http
                .addFilterAt(new LoginFilter(authenticationConfiguration.getAuthenticationManager(), jwtUtil, cookieUtil), UsernamePasswordAuthenticationFilter.class);

        // JWT Filter 등록
        http
                .addFilterAfter(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTExceptionHandler(), LoginFilter.class); // JWTFilter 앞에 예외 처리 필터 추가

        return http.build();
    }

}