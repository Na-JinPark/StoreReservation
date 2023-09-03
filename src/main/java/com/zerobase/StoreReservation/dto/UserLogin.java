package com.zerobase.StoreReservation.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {
    @NotEmpty
    private String userId;
    @NotEmpty
    private String userPassword;

    public static UserLogin from(UserDto userDto){
        return new UserLogin(userDto.getUserId(), userDto.getUserPassword());
    }
}
