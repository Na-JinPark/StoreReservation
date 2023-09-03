package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.type.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private String userId;
    private String userPassword;
    private UserType userType;
    private String nickName;
    private String phoneNumber;

    public static UserDto fromEntity(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .userPassword(user.getUserPassword())
                .userType(user.getUserType())
                .nickName(user.getNickName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

}
