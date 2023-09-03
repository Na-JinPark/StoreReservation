package com.zerobase.StoreReservation.repository;

import com.zerobase.StoreReservation.domain.Reservation;
import com.zerobase.StoreReservation.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findByReservation_ReservationId(Integer reservationId);
}
