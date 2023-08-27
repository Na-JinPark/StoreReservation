package com.zerobase.StoreReservation.service;

import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.dto.UserDto;
import com.zerobase.StoreReservation.exception.UserException;
import com.zerobase.StoreReservation.repository.UserRepository;
import com.zerobase.StoreReservation.type.ErrorCode;
import com.zerobase.StoreReservation.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import static com.zerobase.StoreReservation.type.ErrorCode.REGISTERED_ID;
import static com.zerobase.StoreReservation.type.ErrorCode.REGISTERED_NICKNAME;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //회원 가입
    public UserDto userSignUp(String userId, String userPassword, UserType userType, String nickName){

        validationUserSignUp(userId, nickName);

        return  UserDto.fromEntity(
                userRepository.save(User.builder()
                        .userId(userId)
                        .userPassword(userPassword)
                        .userType(userType)
                        .nickName(nickName)
                        .build())
        );
    }

    private void validationUserSignUp(String userId, String nickName){
        if(userRepository.findByUserId(userId).isPresent()){
            throw new UserException(REGISTERED_ID);
        }
        else if(userRepository.findByNickName(nickName).isPresent()){
            throw new UserException(REGISTERED_NICKNAME);
        }
    }

}
