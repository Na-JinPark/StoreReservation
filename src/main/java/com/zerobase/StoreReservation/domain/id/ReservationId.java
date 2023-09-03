package com.zerobase.StoreReservation.domain.id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
public class ReservationId implements Serializable {
    private LocalDate reservationDate;
    private LocalTime reservationTime;

}