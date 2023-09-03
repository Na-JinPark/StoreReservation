package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.domain.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDto {
    private Integer reviewId;
    private Integer reservationId;
    private Integer grade;
    private String remark;

    public static ReviewDto fromEntity(Review review){
        return ReviewDto.builder()
                .reviewId(review.getReview_id())
                .reservationId(review.getReservation().getReservationId())
                .grade(review.getGrade())
                .remark(review.getRemark())
                .build();
    }
}
