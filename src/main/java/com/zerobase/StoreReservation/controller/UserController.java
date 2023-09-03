package com.zerobase.StoreReservation.controller;

import com.zerobase.StoreReservation.auth.JwtTokenUtil;
import com.zerobase.StoreReservation.dto.UserLogin;
import com.zerobase.StoreReservation.dto.UserSignUp;
import com.zerobase.StoreReservation.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public UserSignUp.Response userSignUp(
            @RequestBody @Valid UserSignUp.Request request
    ) {
        return UserSignUp.Response.from(
                userService.userSignUp(
                        request.getUserId(),
                        request.getUserPassword(),
                        request.getUserType(),
                        request.getNickName()
                )
        );
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> userLogin(
            @ModelAttribute @Valid UserLogin request, HttpServletResponse response
    ) {
        UserLogin user =  UserLogin.from(userService.userLogin(request.getUserId(), request.getUserPassword()));


        String jwtToken = JwtTokenUtil.createToken(user.getUserId());

        // 발급한 Jwt Token을 Cookie를 통해 전송
        // 클라이언트는 다음 요청부터 Jwt Token이 담긴 쿠키 전송 => 이 값을 통해 인증, 인가 진행
        Cookie cookie = new Cookie("jwtToken", jwtToken);
        //cookie.setMaxAge(60 * 60);  // 쿠키 유효 시간 : 1시간
        cookie.setMaxAge(30 * 60);  // 쿠키 유효 시간 : 1시간
        cookie.setPath("/");
        response.addCookie(cookie);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/main");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
