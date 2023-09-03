package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.type.Status;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationInfo {
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private String storeName;
    private String nickName;
    private Status reservationStatus;
    private Status arrivedStatus;
    private LocalDateTime createdTime;
}
