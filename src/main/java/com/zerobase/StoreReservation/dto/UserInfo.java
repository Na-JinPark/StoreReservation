package com.zerobase.StoreReservation.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String userId;
    private String nickName;
    private String phoneNumber;

    public static UserInfo from(UserDto userDto){
        return new UserInfo(userDto.getUserId(), userDto.getNickName(), userDto.getPhoneNumber());
    }
}
