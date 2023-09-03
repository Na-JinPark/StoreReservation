package com.zerobase.StoreReservation.controller;

import com.zerobase.StoreReservation.dto.StoreRegist;
import com.zerobase.StoreReservation.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping("/store")
    public StoreRegist.Response storeRegist(
            @RequestBody @Valid StoreRegist.Request request
    ){
        return StoreRegist.Response.from(storeService.storeRegist(request));
    }
}
