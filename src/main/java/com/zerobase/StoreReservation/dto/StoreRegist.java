package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.type.Status;
import com.zerobase.StoreReservation.type.UserType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalTime;

public class StoreRegist {
    @Getter
    public static class Request{
        @NotEmpty
        private User userId;
        @NotEmpty
        private String storeName;
        private String phoneNumber;
        private String location;
        private LocalTime startTime;
        private LocalTime endTime;
        private Status breakTimeStatus;
        private LocalTime startBreakTime;
        private LocalTime endBreakTime;
        private LocalTime timeSet;
    }

    @Getter
    @Builder
    public static class Response{
        private User userId;
        private String storeName;
        private String phoneNumber;
        private String location;
        private LocalTime startTime;
        private LocalTime endTime;
        private Status breakTimeStatus;
        private LocalTime startBreakTime;
        private LocalTime endBreakTime;
        private LocalTime timeSet;

        public static Response from(StoreDto storeDto){
            return Response.builder()
                    .userId(storeDto.getUserId())
                    .storeName(storeDto.getStoreName())
                    .phoneNumber(storeDto.getPhoneNumber())
                    .location(storeDto.getLocation())
                    .startTime(storeDto.getStartTime())
                    .endTime(storeDto.getEndTime())
                    .breakTimeStatus(storeDto.getBreakTimeStatus())
                    .startBreakTime(storeDto.getStartBreakTime())
                    .endBreakTime(storeDto.getEndBreakTime())
                    .timeSet(storeDto.getTimeSet())
                    .build();
        }
    }
}
