package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.domain.Reservation;
import com.zerobase.StoreReservation.type.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationDto {
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private Integer storeId;
    private String userId;
    private Status reservationStatus;
    private Status arrivedStatus;

    public static ReservationDto fromEntity(Reservation reservation){
        return ReservationDto.builder()
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTime())
                .storeId(reservation.getStore().getStoreId())
                .userId(reservation.getUser().getUserId())
                .reservationStatus(reservation.getReservationStatus())
                .arrivedStatus(reservation.getArrivedStatus())
                .build();
    }
}
