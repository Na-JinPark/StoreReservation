package com.zerobase.StoreReservation.config;

import com.zerobase.StoreReservation.auth.JwtTokenFilter;
import com.zerobase.StoreReservation.exception.UserException;
import com.zerobase.StoreReservation.service.UserService;
import com.zerobase.StoreReservation.type.UserType;
import jakarta.servlet.Filter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static com.zerobase.StoreReservation.type.ErrorCode.UNREGISTERED_ID;

/**
 * Token Login에 사용하는 Security Config
 */
/*@Configuration
@EnableWebSecurity*/
@RequiredArgsConstructor
public class SecurityConfig {

    private Filter JwtTokenFilter;

    /*public static String[] ONLY_MANAGER = { //
                "/admin/access-token",
                "/admin/login-info",
                "/admin/logout",
                "/manage/**", // 유저를 관리하는 API
                "/faq/**" // 자주묻는질문 API
        };*/
    @Bean
    public void securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf((csrf) -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests((authorize) ->
                        authorize
                                .requestMatchers("/login").permitAll() // /user/login API는 인증 없이 접근 가능하도록 설정
                                .anyRequest().authenticated() // 나머지 API는 JWT 토큰 인증이 필요하도록 설정
                )
                .addFilterBefore(JwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin((httpSecurityFormLoginConfigurer) ->
                                httpSecurityFormLoginConfigurer.loginPage("/login").permitAll())
        ;
                /*.exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((request, response, authException) -> {
                                    // 인증 실패 시 처리 로직
                                    response.sendRedirect("/login"); // 예시로 로그인 페이지로 리다이렉션
                                    /*response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized 상태 코드 설정
                                    response.setContentType("application/json;charset=UTF-8"); // JSON 형식으로 응답
                                    response.getWriter().write("{\"error\": \"인증에 실패했습니다.\"}"); // 에러 메시지 반환*/
                                /*})
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    // 인가 실패 시 처리 로직
                                    response.sendRedirect("/access-denied"); // 예시로 접근 거부 페이지로 리다이렉션
                                    // 인가 실패 시 처리 로직
                                    /*response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden 상태 코드 설정
                                    response.setContentType("application/json;charset=UTF-8"); // JSON 형식으로 응답
                                    response.getWriter().write("{\"error\": \"권한이 없습니다.\"}"); // 에러 메시지 반환*/
                               // })
    }
}