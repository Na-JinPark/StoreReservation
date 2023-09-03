package com.zerobase.StoreReservation.controller;

import com.zerobase.StoreReservation.type.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class PageController {
    @GetMapping("/login")
    public String testPage(Model model, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "로그인에 실패했습니다. 다시 시도하세요.");
        }else{
            model.addAttribute("userId", "");
            model.addAttribute("userPassword", "");
        }
        return "login";
    }
    @GetMapping("/main")
    public String mainPage(@CookieValue(name = "jwtToken", required = false) String jwtToken) {

        if (jwtToken == null) {
            // JWT 토큰이 없는 경우 로그인 페이지로 이동
            String redirectUrl = "/login?error=" + URLEncoder.encode(String.valueOf(ErrorCode.REQUIRED_LOGIN), StandardCharsets.UTF_8);
            return "redirect:" + redirectUrl;
        }

        try {
            // JWT 토큰을 해독하여 클레임(Claims) 추출
            Claims claims = Jwts.parser()
                    .setSigningKey("storeReservationKey")
                    .parseClaimsJws(jwtToken)
                    .getBody();

            // userId 추출
            String userId = claims.getSubject();

            return "main";

        } catch (Exception e) {
            // JWT 토큰 해독에 실패하거나 유효하지 않은 토큰인 경우 처리
            e.printStackTrace();
            // 에러 메시지 추가
            String errorMessage = "유효하지 않은 토큰입니다.";
            String redirectUrl = "/login?error=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
            return "redirect:" + redirectUrl;
        }
    }
}
