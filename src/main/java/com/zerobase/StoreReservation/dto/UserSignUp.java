package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.type.UserType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;



public class UserSignUp {
    @Getter
    public static class Request{
        @NotEmpty
        private String userId;
        @NotEmpty
        private String userPassword;
        @NotNull
        private UserType userType;
        @NotEmpty
        private String nickName;
        @NotEmpty
        private String phoneNumber;
    }

    @Getter
    @Builder
    public static class Response{
        private String userId;
        private String nickName;
        private UserType userType;
        private String phoneNumber;

        public static Response from(UserDto userDto){
            return Response.builder()
                    .userId(userDto.getUserId())
                    .nickName(userDto.getNickName())
                    .userType(userDto.getUserType())
                    .phoneNumber(userDto.getPhoneNumber())
                    .build();
        }
    }
}
