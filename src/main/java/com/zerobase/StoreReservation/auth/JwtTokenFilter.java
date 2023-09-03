package com.zerobase.StoreReservation.auth;

import com.zerobase.StoreReservation.domain.CustomUserDetails;
import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.dto.UserDto;
import com.zerobase.StoreReservation.exception.UserException;
import com.zerobase.StoreReservation.repository.UserRepository;
import com.zerobase.StoreReservation.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.zerobase.StoreReservation.type.ErrorCode.UNREGISTERED_ID;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = JwtTokenUtil.resolveToken(request);

        try{
            if (token != null && JwtTokenUtil.isExpired(token)) {
                String userId = JwtTokenUtil.getLoginId(token);

                User user = userService.checkUserID(userId);
                CustomUserDetails userDetails = CustomUserDetails.build(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (JwtException e) {
            // 유효 기간이 만료된 토큰 또는 잘못된 토큰 처리
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    /*
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Header의 Authorization 값이 비어있을 경우 Jwt Token을 전송하지 않는다.
        if(authorizationHeader == null){
            // 화면 로그인 시 쿠키의 "jwtToken"로 Jwt Token을 전송
            // 쿠키에도 Jwt Token이 없다면 로그인 하지 않은 것으로 간주
            if(request.getCookies() == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 쿠키에서 "jwtToken"을 Key로 가진 쿠키를 찾아서 가져오고 없으면 null return
            Cookie jwtCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("jwtToken"))
                    .findFirst()
                    .orElse(null);

            if(jwtCookie == null) {
                filterChain.doFilter(request, response);
                return;
            }
            // 쿠키 Jwt Token이 있다면 이 토큰으로 인증, 인가 진행
            String jwtToken = jwtCookie.getValue();
            authorizationHeader = "Bearer " + jwtToken;
        }

        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
        if(!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
        String token = authorizationHeader.split(" ")[1];

        // 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
        if(JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Jwt Token에서 loginId 추출
        String loginId = JwtTokenUtil.getLoginId(token, secretKey);

        // 추출한 loginId로 User 찾아오기
        UserDto loginUser =  userService.checkUserID(loginId);

        // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getUserId(), null, List.of(new SimpleGrantedAuthority(loginUser.getUserType().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }*/
}