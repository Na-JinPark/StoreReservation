package com.zerobase.StoreReservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    REGISTERED_ID("현재 사용중인 ID 입니다."),
    REGISTERED_NICKNAME("현재 사용중인 닉네임 입니다."),
    REGISTERED_STOREID("현재 사용중인 사업자 등록번호 입니다."),
    REQUIRED_INPUT("입력이 필요합니다."),
    NOT_MANAGER_USER("파트너 회원이 아닙니다."),
    ;

    private final String description;

}
