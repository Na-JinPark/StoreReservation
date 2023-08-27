package com.zerobase.StoreReservation.controller;

import com.zerobase.StoreReservation.dto.UserSignUp;
import com.zerobase.StoreReservation.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

}
