package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.type.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationStatus {
    @Getter
    public static class Request{
        @NotNull
        private Integer reservationId;

    }

    @Getter
    @Builder
    public static class Response{
        private LocalDate reservationDate;
        private LocalTime reservationTime;
        private Integer storeId;
        private String userId;
        private Status reservationStatus;
        private Status arrivedStatus;
        public static ReservationStatus.Response from(ReservationDto reservationDto){
            return Response.builder()
                    .reservationDate(reservationDto.getReservationDate())
                    .reservationTime(reservationDto.getReservationTime())
                    .storeId(reservationDto.getStoreId())
                    .userId(reservationDto.getUserId())
                    .reservationStatus(reservationDto.getReservationStatus())
                    .arrivedStatus(reservationDto.getArrivedStatus())
                    .build();
        }
    }

}
