package com.zerobase.StoreReservation.service;

import com.zerobase.StoreReservation.auth.JwtTokenUtil;
import com.zerobase.StoreReservation.domain.Reservation;
import com.zerobase.StoreReservation.domain.Store;
import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.dto.ReservationDto;
import com.zerobase.StoreReservation.dto.ReservationList;
import com.zerobase.StoreReservation.exception.UserException;
import com.zerobase.StoreReservation.repository.ReservationRepository;
import com.zerobase.StoreReservation.repository.StoreRepository;
import com.zerobase.StoreReservation.repository.UserRepository;
import com.zerobase.StoreReservation.type.Status;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.zerobase.StoreReservation.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public List<ReservationList> getReservationList(LocalDate reservationDate, Integer storerId) {
        List<ReservationList> test =  reservationRepository.getReservationList(storerId, reservationDate);
        return test;
    }

    public ReservationDto reservationRegist(LocalDate reservationDate, LocalTime reservationTime, Integer storeID,  HttpServletRequest httpRequest){

        // JWT 토큰 가져오기
        String jwtToken = httpRequest.getHeader("Authorization").substring(7);

        // JWT 토큰에서 사용자 ID 추출
        String userId = JwtTokenUtil.getLoginId(jwtToken);

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UNREGISTERED_ID));

        Store store = storeRepository.findByStoreId(storeID)
                .orElseThrow(()->new UserException(UNREGISTERED_STORE_ID));


        return  ReservationDto.fromEntity(
                reservationRepository.save(Reservation.builder()
                                .reservationDate(reservationDate)
                                .reservationTime(reservationTime)
                                .store(store)
                                .user(user)
                                .reservationStatus(Status.N)
                                .arrivedStatus(Status.N)
                        .build())
        );
    }

    public ReservationDto reservationConfirm(Integer reservationId){

        Reservation reservation = reservationRepository.findById(reservationId)
                                                .orElseThrow(()-> new UserException(UNREGISTERED_RESERVATION));

        reservation.setReservationStatus(Status.Y);

       return  ReservationDto.fromEntity(
                reservationRepository.save(reservation)
        );
    }

    public ReservationDto reservationArrived(Integer reservationId){

        Reservation reservation =  reservationRepository.findById(reservationId)
                .orElseThrow(()-> new UserException(UNREGISTERED_RESERVATION));

        reservation.setArrivedStatus(Status.Y);

        return  ReservationDto.fromEntity(
                reservationRepository.save(reservation)
        );
    }
}
