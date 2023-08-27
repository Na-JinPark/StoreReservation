package com.zerobase.StoreReservation.domain;

import com.zerobase.StoreReservation.type.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "store")
@EntityListeners(AuditingEntityListener.class)
public class Store {

    @Id
    private String userId;
    @Id
    private BigInteger storeId;

    private String storeName;
    private String phoneNumber;
    private String location;
    private LocalTime startTime;
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private Status breakTimeStatus;

    private LocalTime startBreakTime;
    private LocalTime endBreakTime;
    private LocalTime timeSet;

    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;

}
