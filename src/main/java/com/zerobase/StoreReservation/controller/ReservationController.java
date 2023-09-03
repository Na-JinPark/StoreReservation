package com.zerobase.StoreReservation.controller;

import com.zerobase.StoreReservation.domain.Reservation;
import com.zerobase.StoreReservation.dto.*;
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

    @PostMapping("/reservation/confirm")
    public ReservationStatus.Response reservationStatus(
            @RequestBody @Valid ReservationStatus.Request request
    ){
        return ReservationStatus.Response.from(
                reservationService.reservationStatus(
                        request.getReservationDate(),
                        request.getReservationTime(),
                        request.getStoreId()
                )
        );
    }

    @GetMapping("/reservation/list")
    public List<ReservationList> getReservationList(
            @RequestParam("reservation_date") LocalDate reservationDate,
            @RequestParam("store_id") int storeId
    ){
        return reservationService.getReservationList(reservationDate, storeId);
    }

}
