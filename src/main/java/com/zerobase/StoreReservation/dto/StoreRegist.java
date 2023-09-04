package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.type.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

public class StoreRegist {
    @Getter
    public static class Request{
        @NotNull
        private Integer storeId;
        @NotEmpty
        private String userId;
        @NotEmpty
        private String storeName;
        private String remark;
        private String phoneNumber;
        private String location;
        @NotNull
        private LocalTime startTime;
        @NotNull
        private LocalTime endTime;
        private Status breakTimeStatus = Status.N;
        private LocalTime startBreakTime;
        private LocalTime endBreakTime;
        @NotNull
        private LocalTime timeSet;
    }

    @Getter
    @Builder
    public static class Response{
        private Integer storeId;
        private String userId;
        private String storeName;
        private String remark;
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
                    .storeId(storeDto.getStoreId())
                    .userId(storeDto.getUserId())
                    .storeName(storeDto.getStoreName())
                    .remark(storeDto.getRemark())
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
