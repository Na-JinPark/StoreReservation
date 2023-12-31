package com.zerobase.StoreReservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    REQUIRED_LOGIN("로그인을 해주세요."),
    UNREGISTERED_ID("잘못된 ID 입니다."),
    UNREGISTERED_STORE_ID("등록되지 않은 식당 입니다."),
    UNREGISTERED_RESERVATION("등록 되지 않은 예약 입니다."),
    REGISTERED_ID("현재 사용중인 ID 입니다."),
    UNREGISTERED_PASSWORD("잘못된 비밀번호 입니다."),
    REGISTERED_NICKNAME("현재 사용중인 닉네임 입니다."),
    REGISTERED_STORE_ID("현재 사용중인 사업자 등록번호 입니다."),
    REQUIRED_INPUT("입력이 필요합니다."),
    NOT_MANAGER_USER("파트너 회원이 아닙니다."),
    UNREGISTERED_RESERVATION_REVIEW("리뷰는 한번만 쓸 수 있습니다."),
    UNUSED_RESERVATION("리뷰는 예약시간 기준 10분 이후 부터 작성할 수 있습니다."),
    PULL_RESERVATION("예약 가능한 시간이 없습니다."),
    ALREADY_REGISTERED_RESERVATION("이미 등록된 예약 시간 입니다."),
    ;

    private final String description;

}
