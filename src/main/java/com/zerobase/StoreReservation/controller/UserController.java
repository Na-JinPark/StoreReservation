package com.zerobase.StoreReservation.controller;

import com.zerobase.StoreReservation.auth.JwtTokenUtil;
import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.dto.*;
import com.zerobase.StoreReservation.exception.UserException;
import com.zerobase.StoreReservation.service.UserService;
import com.zerobase.StoreReservation.type.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /*
    * 회원가입 api
    * 파라미터 : 사용자id, 비밀번호, 사용자 타입(일반, 파트너), 닉네임, 전화번호
    * 성공응답 : 사용자아이디, 닉네임, 전화번호
    */
    @PostMapping("/user")
    public UserSignUp.Response userSignUp(
            @RequestBody @Valid UserSignUp.Request request
    ) {
        return UserSignUp.Response.from(
                userService.userSignUp(
                        request.getUserId(),
                        request.getUserPassword(),
                        request.getUserType(),
                        request.getNickName(),
                        request.getPhoneNumber()
                )
        );
    }

    /*
     * 로그인 api
     * 파라미터 : 사용자아이디, 비밀번호
     * 성공시 main.html로 이동
     */
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

    /*
     * 사용자 정보 조회
     * 발급된 jwtToken에서 userId를 가져와 사용자 정보 조회
     * 정책 : jwtToken이 없을 경우 실패
     */
    @GetMapping("/user/Info")
    public UserInfo getUserInfo(HttpServletRequest httpRequest){

        String jwtToken = JwtTokenUtil.resolveToken(httpRequest);

        if(jwtToken == null) new UserException(ErrorCode.REQUIRED_LOGIN);

        String userId = JwtTokenUtil.getLoginId(jwtToken);

        UserInfo userInfo = UserInfo.from(UserDto.fromEntity(userService.checkUserId(userId)));

        return userInfo;
    }
}
