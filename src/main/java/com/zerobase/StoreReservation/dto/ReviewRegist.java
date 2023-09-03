package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.domain.Review;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReviewRegist {
    @Getter
    public static class Request{
        @NotNull
        private Integer reservationId;
    }

    @Getter
    @Builder
    public static class Response{
        private String nickname;
        private LocalDate reservationDate;
        private LocalTime reservationTime;
        private Integer grade;
        private String remark;

        public static Response from(ReviewDto reviewDto, String nickname) {
            return Response.builder()
                    .nickname(nickname)
                    .grade(reviewDto.getGrade())
                    .remark(reviewDto.getRemark())
                    .build();
        }
    }
}
