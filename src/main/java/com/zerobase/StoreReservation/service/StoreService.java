package com.zerobase.StoreReservation.service;

import com.zerobase.StoreReservation.domain.Store;
import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.dto.StoreDto;
import com.zerobase.StoreReservation.dto.StoreRegist;
import com.zerobase.StoreReservation.exception.UserException;
import com.zerobase.StoreReservation.repository.StoreRepository;
import com.zerobase.StoreReservation.type.Status;
import com.zerobase.StoreReservation.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.StoreReservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserService userService;

    /*
     * 매장등록
     * 파라미터 : 필수 -> 매장아이디, 사용자아이디, 매장이름, 영업시작시간, 종료시간, timeSet
     *          그 외 -> 설명, 전화번호, 위치, 브레이크 타임여부, 브레이크타임 시작시간, 종료시간
     * 정책 : 매장 아이디가 중복일 경우,
     *       사용자가 파트너 회원이 아닐 경우
     *       브레이크 타임여부 = 'Y' 일 때 브레이크 타임 시작시간 및 종료시간을 입력하지 않았을 경우 실패
     */
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

    /*
     * 매장 아이디가 중복인지 확인
     * 브레이크 타임여부 = 'Y' 일 경우 브레이크 타임 시작 시간 및 종료시간 입력 확인
     */
    private void validationStoreRegist(Store store){
        if(storeRepository.findByStoreId(store.getStoreId()).isPresent()){
            throw new UserException(REGISTERED_STORE_ID);
        }
        else if(store.getBreakTimeStatus().equals(Status.Y)){
            if(store.getStartBreakTime()== null || store.getEndBreakTime()== null){
                throw new UserException(REQUIRED_INPUT,"브레이크 타임이 있을 경우 브레이크타임의 시작시간과 종료시간을 입력해 주세요.");
            }
        }
    }

    // 사용자가 파트너 회원인지 확인
    private User checkUserType(String userId){
        User user =  userService.checkUserId(userId);
        if(user.getUserType().equals(UserType.GENERAL)){
            throw new UserException(NOT_MANAGER_USER);
        }
        return user;
    }

    /*
     * 매장 목록 조회
     */
    public List<StoreDto> getStoreList(){
        Sort sort = Sort.by(
                Sort.Order.asc("storeName"), // store_name를 가나다라 순으로 오름차순 정렬
                Sort.Order.desc("aevrageGrade") // average_grade를 내림차순 정렬
        );
        List<Store> stores = storeRepository.findAll(sort);

        return stores.stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }

    public StoreDto getStoreInfo(Integer storeId){
        Store store = storeCheck(storeId);
        return StoreDto.fromEntity(store);
    }

    public Store storeCheck(Integer storeId){
        Store store = storeRepository.findByStoreId(storeId)
                .orElseThrow(()->new UserException(UNREGISTERED_STORE_ID));
        return store;
    }

}
