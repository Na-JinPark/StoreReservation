package com.zerobase.StoreReservation.domain;

import com.zerobase.StoreReservation.domain.id.ReservationId;
import com.zerobase.StoreReservation.type.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reservation")
@IdClass(ReservationId.class)
@EntityListeners(AuditingEntityListener.class)
public class Reservation {
    @Id
    private LocalDate reservationDate;
    @Id
    private LocalTime reservationTime;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Status reservationStatus;

    @Enumerated(EnumType.STRING)
    private Status arrivedStatus;

    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;

}
