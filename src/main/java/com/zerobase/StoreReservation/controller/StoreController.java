package com.zerobase.StoreReservation.controller;

import com.zerobase.StoreReservation.dto.StoreDto;
import com.zerobase.StoreReservation.dto.StoreInfo;
import com.zerobase.StoreReservation.dto.StoreRegist;
import com.zerobase.StoreReservation.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    /*
     * 매장등록 api
     * 파라미터 : 필수 -> 매장아이디, 사용자아이디, 매장이름, 영업시작시간, 종료시간, 예약시간
     *          그 외 -> 설명, 전화번호, 위치, 브레이크 타임여부, 브레이크타임 시작시간, 종료시간
     * 성공응답 : 매장아이디, 사용자아이디, 매장이름, 영업시작시간, 종료시간, 예약시간, 설명, 전화번호, 위치, 브레이크 타임여부, 브레이크타임 시작시간, 종료시간
     */
    @PostMapping("/store")
    public StoreRegist.Response storeRegist(
            @RequestBody @Valid StoreRegist.Request request
    ){
        return StoreRegist.Response.from(storeService.storeRegist(request));
    }

    /*
     * 매장 목록 조회
     */
    @GetMapping("/store/list")
    public List<StoreInfo> getStoreList(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return storeService.getStoreList()
                .stream().map(storeDto ->
                        StoreInfo.builder()
                                .storeName(storeDto.getStoreName())
                                .remark(storeDto.getRemark())
                                .phoneNumber(storeDto.getPhoneNumber())
                                .location(storeDto.getLocation())
                                .average_grade(storeDto.getAverageGrade())
                                .businesTime(storeDto.getStartTime().format(formatter) + "~" + storeDto.getEndTime().format(formatter))
                                .breakTime(storeDto.getStartBreakTime().format(formatter) + "~" + storeDto.getEndBreakTime().format(formatter))
                                .build())
                .collect(Collectors.toList());
    }

    /*
     * 매장 조회
     * 파라미터 : 상점 아이디
     */
    @GetMapping("/store/Info")
    public StoreInfo getStoreInfo(
            @RequestParam("store_id") int storeId
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        StoreDto storeDto = storeService.getStoreInfo(storeId);
        return  StoreInfo.builder()
                .storeName(storeDto.getStoreName())
                .remark(storeDto.getRemark())
                .phoneNumber(storeDto.getPhoneNumber())
                .location(storeDto.getLocation())
                .average_grade(storeDto.getAverageGrade())
                .businesTime(storeDto.getStartTime().format(formatter) + "~" + storeDto.getEndTime().format(formatter))
                .breakTime(storeDto.getStartBreakTime().format(formatter) + "~" + storeDto.getEndBreakTime().format(formatter))
                .build();
    }

}
