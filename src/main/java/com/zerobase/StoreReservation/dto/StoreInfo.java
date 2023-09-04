package com.zerobase.StoreReservation.dto;

import lombok.Builder;

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
