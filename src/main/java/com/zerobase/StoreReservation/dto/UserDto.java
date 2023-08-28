package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.type.UserType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    private String userId;
    private String userPassword;
    private UserType userType;
    private String nickName;

    public static UserDto fromEntity(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .userPassword(user.getUserPassword())
                .userType(user.getUserType())
                .nickName(user.getNickName())
                .build();
    }

}