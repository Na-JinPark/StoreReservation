package com.zerobase.StoreReservation.service;

//import com.zerobase.StoreReservation.auth.JwtTokenUtil;

import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.dto.UserDto;
import com.zerobase.StoreReservation.exception.UserException;
import com.zerobase.StoreReservation.repository.UserRepository;
import com.zerobase.StoreReservation.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zerobase.StoreReservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /*
     * 회원가입
     * 파라미터 : 사용자id, 비밀번호, 사용자 타입(일반, 파트너), 닉네임, 전화번호
     * 정책 : 사용자 아이디, 닉네임 중복 시 실패 응답
     */
    public UserDto userSignUp(String userId, String userPassword, UserType userType, String nickName, String phoneNumber){

        validationUserSignUp(userId, nickName);

        return  UserDto.fromEntity(
                userRepository.save(User.builder()
                        .userId(userId)
                        .userPassword(userPassword)
                        .userType(userType)
                        .nickName(nickName)
                        .phoneNumber(phoneNumber)
                        .build())
        );
    }

    /*
    * 회원 가입 전 사용중인 아이디와 닉네임인지 확인
    */
    private void validationUserSignUp(String userId, String nickName){
        if(userRepository.findById(userId).isPresent()){
            throw new UserException(REGISTERED_ID);
        }
        else if(userRepository.findByNickName(nickName).isPresent()){
            throw new UserException(REGISTERED_NICKNAME);
        }
    }

    /*
     * 로그인
     * 파라미터 : 사용자아이디, 비밀번호
     * 정책 : 등록되지 않은 사용자 아이디일 경우, 아이디는 존재하나 비밀번호가 다를 경우 실패
     */
    public UserDto userLogin(String userId, String userPassword){

        User user = checkUserId(userId);

        if(!user.getUserPassword().equals(userPassword))
            new UserException(UNREGISTERED_PASSWORD);

       return UserDto.fromEntity(user);
    }

    /*
     * 사용자 아이디 존재여부 확인
     */
    public User checkUserId(String userId){
        User loginUser =  userRepository.findById(userId)
                .orElseThrow(()->new UserException(UNREGISTERED_ID));
        return loginUser;

    }

}
