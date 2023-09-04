package com.zerobase.StoreReservation.controller;

import com.zerobase.StoreReservation.dto.ReservationList;
import com.zerobase.StoreReservation.dto.ReservationRegist;
import com.zerobase.StoreReservation.dto.ReservationStatus;
import com.zerobase.StoreReservation.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    /*
     * 예약등록 api
     * 파라미터 : 예약일자, 예약시간, 매장아이디
     * 성공응답 : 예약일자, 예약시간, 매장아이디, 사용자 아이디
     */
    @PostMapping("/reservation")
    public ReservationRegist.Response reservationRegist(
            @RequestBody @Valid ReservationRegist.Request request,
            HttpServletRequest httpRequest
    ){
        return ReservationRegist.Response.from(
                reservationService.reservationRegist(
                        request.getReservationDate(),
                        request.getReservationTime(),
                        request.getStoreId(),
                        httpRequest
                )
        );
    }

    /*
     * 예약확인 api
     * 파라미터 : 예약번호
     * 성공응답 : 예약일자, 예약시간, 매장아이디, 사용자 아이디, 예약상태, 도착상태
     */
    @PostMapping("/reservation/confirm")
    public ReservationStatus.Response reservationConfirm(
            @RequestBody @Valid ReservationStatus.Request request
    ){
        return ReservationStatus.Response.from(
                reservationService.reservationConfirm(
                        request.getReservationId()
                )
        );
    }

    /*
     * 예약거절 api
     * 파라미터 : 예약번호
     * 성공응답 : 예약일자, 예약시간, 매장아이디, 사용자 아이디, 예약상태, 도착상태
     */
    @PostMapping("/reservation/refuse")
    public ReservationStatus.Response reservationRefuse(
            @RequestBody @Valid ReservationStatus.Request request
    ){
        return ReservationStatus.Response.from(
                reservationService.reservationRefuse(
                        request.getReservationId()
                )
        );
    }

    /*
     * 도착확인 api
     * 파라미터 : 예약번호
     * 성공응답 : 예약일자, 예약시간, 매장아이디, 사용자 아이디, 예약상태, 도착상태
     */
    @PostMapping("/reservation/arrived")
    public ReservationStatus.Response reservationArrived(
            @RequestBody @Valid ReservationStatus.Request request
    ){
        return ReservationStatus.Response.from(
                reservationService.reservationArrived(
                        request.getReservationId()
                )
        );
    }

    /*
     * 예약가능 시간 조회 api
     * 파라미터 : 예약일자, 매장아이디
     * 성공응답 : 예약가능시간
     */
    @GetMapping("/reservation/list")
    public List<ReservationList> getReservationList(
            @RequestParam("reservation_date") LocalDate reservationDate,
            @RequestParam("store_id") int storeId
    ){
        return reservationService.getReservationList(reservationDate, storeId);
    }


}
