package com.zerobase.StoreReservation.service;

import com.zerobase.StoreReservation.domain.Reservation;
import com.zerobase.StoreReservation.domain.Review;
import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.dto.ReservationDto;
import com.zerobase.StoreReservation.dto.ReviewDto;
import com.zerobase.StoreReservation.dto.ReviewRegist;
import com.zerobase.StoreReservation.dto.UserDto;
import com.zerobase.StoreReservation.exception.UserException;
import com.zerobase.StoreReservation.repository.ReviewRepository;
import com.zerobase.StoreReservation.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.zerobase.StoreReservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationService reservationService;
    public ReviewDto reviewRegist(Integer reservationId, Integer grade, String remark){

        if(reviewRepository.findByReservation_ReservationId(reservationId).isPresent()){
            throw new UserException(UNREGISTERED_RESERVATION_REVIEW);
        }

        Reservation reservation = reservationService.reservationInfo(reservationId);

        LocalDateTime reservationTime = LocalDateTime.of(reservation.getReservationDate(), reservation.getReservationTime());
        if(reservationTime.isBefore(LocalDateTime.now().plusMinutes(10))){
            throw new UserException(UNUSED_RESERVATION);
        }

        return  ReviewDto.fromEntity(
                reviewRepository.save(Review.builder()
                        .reservation(reservation)
                        .grade(grade)
                        .remark(remark)
                        .build())
        );
    }

}