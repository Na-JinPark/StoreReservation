package com.zerobase.StoreReservation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRegist {
    @Getter
    public static class Request{
        @NotNull
        private LocalDate reservationDate;
        @NotNull
        private LocalTime reservationTime;
        @NotNull
        private Integer storeId;
    }

    @Getter
    @Builder
    public static class Response{
        private LocalDate reservationDate;
        private LocalTime reservationTime;
        private Integer storeId;
        private String userId;
        public static Response from(ReservationDto reservationDto){
            return Response.builder()
                    .reservationDate(reservationDto.getReservationDate())
                    .reservationTime(reservationDto.getReservationTime())
                    .storeId(reservationDto.getStoreId())
                    .userId(reservationDto.getUserId())
                    .build();
        }
    }
}
