package com.zerobase.StoreReservation.controller;

import com.zerobase.StoreReservation.dto.ReviewRegist;
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

    /*
     * 리뷰등록 api
     * 파라미터 : 예약번호, 평점, 리뷰
     * 성공응답 : 평점, 리뷰
     */
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
