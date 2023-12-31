package com.zerobase.StoreReservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public class ReviewRegist {
    @Getter
    public static class Request{
        @NotNull
        private Integer reservationId;
        @NotNull
        private Integer grade;
        @NotNull
        private String remark;
    }

    @Getter
    @Builder
    public static class Response{
        private Integer grade;
        private String remark;

        public static Response from(ReviewDto reviewDto) {
            return Response.builder()
                    .grade(reviewDto.getGrade())
                    .remark(reviewDto.getRemark())
                    .build();
        }
    }
}
