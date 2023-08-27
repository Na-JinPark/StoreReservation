package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.domain.Store;
import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.type.Status;
import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalTime;

@Getter
@Builder
public class StoreDto {
    private User userId;
    private BigInteger storeId;
    private String storeName;
    private String phoneNumber;
    private String location;
    private LocalTime startTime;
    private LocalTime endTime;
    private Status breakTimeStatus;
    private LocalTime startBreakTime;
    private LocalTime endBreakTime;
    private LocalTime timeSet;

    public static StoreDto fromEntity(Store store){
        return StoreDto.builder()
                .userId(store.getUserId())
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .phoneNumber(store.getPhoneNumber())
                .location(store.getLocation())
                .startTime(store.getStartTime())
                .endTime(store.getEndTime())
                .breakTimeStatus(store.getBreakTimeStatus())
                .startBreakTime(store.getStartBreakTime())
                .endBreakTime(store.getEndBreakTime())
                .timeSet(store.getTimeSet())
                .build();
    }
}
