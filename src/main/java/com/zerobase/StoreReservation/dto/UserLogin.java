package com.zerobase.StoreReservation.dto;

import com.zerobase.StoreReservation.type.UserType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
