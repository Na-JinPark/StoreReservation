package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.type.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Builder
public class StoreInfo {
    private String storeName;
    private String remark;
    private String phoneNumber;
    private String location;
    private Integer average_grade;
    private String businesTime;
    private String breakTime;

}
