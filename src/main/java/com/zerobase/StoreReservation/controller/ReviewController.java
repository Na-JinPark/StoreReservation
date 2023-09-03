package com.zerobase.StoreReservation.controller;

import com.zerobase.StoreReservation.domain.Review;
import com.zerobase.StoreReservation.dto.ReviewRegist;
import com.zerobase.StoreReservation.dto.StoreRegist;
import com.zerobase.StoreReservation.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/review")
    public ReviewRegist.Response reviewRegist(
            @RequestBody @Valid ReviewRegist.Request request
    ){
        return ReviewRegist.Response.from(
                reviewService.reviewRegist(
                        request.getReservationId(),
                        request.getGrade(),
                        request.getRemark()
                ));
    }
}
