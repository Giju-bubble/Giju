package com.bubble.giju.global.jwt;

import com.bubble.giju.domain.user.dto.CustomPrincipal;
import com.bubble.giju.domain.user.dto.LoginDto;
import com.bubble.giju.global.config.ApiResponse;
import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final ObjectMapper objectMapper;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, CookieUtil cookieUtil, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        this.objectMapper = objectMapper;

        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // json으로 받기 위해 사용
        try {
            LoginDto.LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginDto.LoginRequest.class);

            String username = loginRequest.getLoginId();
            String password = loginRequest.getPassword();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INVALID_LOGIN_JSON);
        } catch (AuthenticationException e) {
            // 인증 예외는 이미 unsuccessfulAuthentication에서 처리됨
            throw e;
        } catch (Exception e) {
            // 기타 예상치 못한 예외
            log.error("Unexpected error during authentication", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successful authentication");

        CustomPrincipal principal = (CustomPrincipal) authResult.getPrincipal();

        String username = principal.getUsername();
        String userId = principal.getUserId();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority().replace("ROLE_", "");

        // 토큰 발행
        String accessToken = jwtUtil.createAccessToken(username, role, userId);
        String refreshToken = jwtUtil.createRefreshToken(username, role, userId);

        // TODO: 토큰 db 저장
//        refreshTokenService.addRefreshToken(username, refreshToken, jwtUtil.getRefreshExpiration());

        // 응답 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("access", accessToken);
        response.addCookie(cookieUtil.createCookie("refresh", refreshToken));
        response.setStatus(HttpStatus.OK.value());

        LoginDto.LoginResponse loginResponse = LoginDto.LoginResponse.of(accessToken, refreshToken);
        ApiResponse<LoginDto.LoginResponse> apiResponse = ApiResponse.success("로그인 성공", loginResponse);

        objectMapper.writeValue(response.getWriter(), apiResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 응답 설정
        throw new CustomException(ErrorCode.LOGIN_UNAUTHORIZED);
    }
}
