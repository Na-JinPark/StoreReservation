package com.zerobase.StoreReservation.service;

import com.zerobase.StoreReservation.domain.Store;
import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.dto.StoreDto;
import com.zerobase.StoreReservation.dto.StoreRegist;
import com.zerobase.StoreReservation.exception.UserException;
import com.zerobase.StoreReservation.repository.StoreRepository;
import com.zerobase.StoreReservation.repository.UserRepository;
import com.zerobase.StoreReservation.type.Status;
import com.zerobase.StoreReservation.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.zerobase.StoreReservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public StoreDto storeRegist(StoreRegist.Request request){
        User user =  checkUserType(request.getUserId());

        Store store = Store.builder()
                           .storeId(request.getStoreId())
                           .remark(request.getRemark())
                           .user(user)
                           .storeName(request.getStoreName())
                           .location(request.getLocation())
                           .startTime(request.getStartTime())
                           .endTime(request.getEndTime())
                           .breakTimeStatus(request.getBreakTimeStatus())
                           .startBreakTime(request.getStartBreakTime())
                           .endBreakTime(request.getEndBreakTime())
                           .timeSet(request.getTimeSet())
                           .build();

        validationStoreRegist(store);

        return  StoreDto.fromEntity(storeRepository.save(store));
    }

    private void validationStoreRegist(Store store){
        if(storeRepository.findByStoreId(store.getStoreId()).isPresent()){
            throw new UserException(REGISTERED_STOREID);
        }
        else if(store.getBreakTimeStatus().equals(Status.Y)){
            if(store.getStartBreakTime()== null || store.getEndBreakTime()== null){
                throw new UserException(REQUIRED_INPUT,"브레이크 타임이 있을 경우 브레이크타임의 시작시간과 종료시간을 입력해 주세요.");
            }
        }
    }

    private User checkUserType(String userId){
        User user =  userRepository.findByUserId(userId).get();
        if(user.getUserType().equals(UserType.GENERAL)){
            throw new UserException(NOT_MANAGER_USER);
        }
        return user;
    }

}
