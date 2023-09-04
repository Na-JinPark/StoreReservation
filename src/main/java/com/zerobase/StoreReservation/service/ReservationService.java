package com.zerobase.StoreReservation.service;

import com.zerobase.StoreReservation.auth.JwtTokenUtil;
import com.zerobase.StoreReservation.domain.Reservation;
import com.zerobase.StoreReservation.domain.Store;
import com.zerobase.StoreReservation.domain.User;
import com.zerobase.StoreReservation.dto.ReservationDto;
import com.zerobase.StoreReservation.dto.ReservationList;
import com.zerobase.StoreReservation.exception.UserException;
import com.zerobase.StoreReservation.repository.ReservationRepository;
import com.zerobase.StoreReservation.type.ErrorCode;
import com.zerobase.StoreReservation.type.Status;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.zerobase.StoreReservation.type.ErrorCode.ALREADY_REGISTERED_RESERVATION;
import static com.zerobase.StoreReservation.type.ErrorCode.REQUIRED_LOGIN;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final StoreService storeService;


    /*
     * 예약가능 시간 조회
     * 파라미터 : 예약일자, 매장아이디
     * 정책 : 매장정보의 오픈시간 부터 종료시간까지 timeSet에 저장된 시간 간격으로 예약가능 시간 조회, 브레이크 타임여부가 y일 경우 브레이크타임시간도 적용
     *       예약가능 시간이 없을경우 실패
     */
    public List<ReservationList> getReservationList(LocalDate reservationDate, Integer storerId) {
        List<ReservationList> reservationList =  reservationRepository.getReservationList(storerId, reservationDate);
        if(reservationList.size() == 0) new UserException(ErrorCode.PULL_RESERVATION);
        return reservationList;
    }

    /*
     * 예약 등록
     * 파라미터 : 예약일자, 예약시간, 매장아이디, httpRequest
     * 정책 : jwt 토큰이 없을 경우 실패, jwt 토큰에 담긴 userId가 잘못된 정보일 경우 실패, 같은 날짜와 시간에 예약이 있을 경우 실패
     */
    public ReservationDto reservationRegist(LocalDate reservationDate, LocalTime reservationTime, Integer storeID,  HttpServletRequest httpRequest){

        String jwtToken = JwtTokenUtil.resolveToken(httpRequest);

        if(jwtToken == null) new UserException(REQUIRED_LOGIN);

        String userId = JwtTokenUtil.getLoginId(jwtToken);

        User user = userService.checkUserId(userId);
        Store store = storeService.storeCheck(storeID);

        if(reservationRepository.findByReservationDateAndReservationTimeAndStore_StoreId(reservationDate, reservationTime, storeID).isPresent()){
            new UserException(ALREADY_REGISTERED_RESERVATION);
        }

        return  ReservationDto.fromEntity(
                reservationRepository.save(Reservation.builder()
                                .reservationDate(reservationDate)
                                .reservationTime(reservationTime)
                                .store(store)
                                .user(user)
                                .arrivedStatus(Status.N)
                        .build())
        );
    }

    /*
     * 예약 확인
     * 파라미터 : 예약번호
     * 정책 : 예약번호가 존재하지 않을 경우 실패
     */
    public ReservationDto reservationConfirm(Integer reservationId){

        Reservation reservation = reservationCheck(reservationId);

        reservation.setReservationStatus(Status.Y);

       return  ReservationDto.fromEntity(
                reservationRepository.save(reservation)
        );
    }

    /*
     * 예약 거절
     * 파라미터 : 예약번호
     * 정책 : 예약번호가 존재하지 않을 경우 실패
     */
    public ReservationDto reservationRefuse(Integer reservationId){

        Reservation reservation = reservationCheck(reservationId);

        reservation.setReservationStatus(Status.N);

        return  ReservationDto.fromEntity(
                reservationRepository.save(reservation)
        );
    }

    /*
     * 도착확인
     * 파라미터 : 예약번호
     * 정책 : 예약번호가 존재하지 않을 경우 실패
     */
    public ReservationDto reservationArrived(Integer reservationId){

        Reservation reservation = reservationCheck(reservationId);

        reservation.setArrivedStatus(Status.Y);

        return  ReservationDto.fromEntity(
                reservationRepository.save(reservation)
        );
    }

    /*
     * 예약정보 가져오기
     * 파라미터 : 예약번호
     * 정책 : 예약번호가 존재하지 않을 경우 실패
     */
    public Reservation reservationInfo(Integer reservationId){
        Reservation reservation = reservationCheck(reservationId);
        return  reservation;
    }

    /*
     * 예약번호가 존재하지 않을 경우 실패
     */
    private Reservation reservationCheck(Integer reservationId){
        Reservation reservation =  reservationRepository.findById(reservationId)
                .orElseThrow(()-> new UserException(ErrorCode.UNREGISTERED_RESERVATION));

        return reservation;
    }
}
