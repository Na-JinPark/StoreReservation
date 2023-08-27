package com.zerobase.StoreReservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    REGISTERED_ID("ID가 중복 되었습니다."),
    REGISTERED_NICKNAME("닉네임이 중복되었습니다.")
    ;

    private final String description;

}
