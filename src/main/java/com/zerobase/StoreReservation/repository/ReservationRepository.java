package com.zerobase.StoreReservation.repository;

import com.zerobase.StoreReservation.domain.Reservation;
import com.zerobase.StoreReservation.dto.ReservationList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query(value = "WITH RECURSIVE temp AS ( " +
            "    SELECT start_time AS reservation_time, store_id, break_time_status, start_break_time, end_break_time " +
            "    FROM store " +
            "    WHERE store_id = :storeId " +
            "    UNION ALL " +
            "    SELECT ADDTIME(t1.reservation_time, t2.time_set) AS reservation_time, t2.store_id, t2.break_time_status, t2.start_break_time, t2.end_break_time " +
            "    FROM temp t1 " +
            "    INNER JOIN store t2 ON t1.store_id = t2.store_id " +
            "    WHERE t1.store_id = :storeId AND ADDTIME(t1.reservation_time, t2.time_set) <= t2.end_time " +
            ") " +
            "SELECT t1.reservation_time as reservationTime " +
            "FROM temp t1 " +
            "LEFT JOIN reservation t2 ON t1.reservation_time = t2.reservation_time AND t2.reservation_date = :reservationDate " +
            "WHERE CASE WHEN t1.break_time_status = 'Y' THEN (t1.reservation_time < t1.start_break_time OR t1.reservation_time >= t1.end_break_time) ELSE t1.reservation_time END " +
            "AND IFNULL(t2.reservation_status, '') != 'Y'",
            nativeQuery = true)
    List<ReservationList> getReservationList(@Param("storeId") Integer storeId, @Param("reservationDate") LocalDate reservationDate);

    Optional<Reservation> findByReservationDateAndReservationTimeAndStore_StoreId(LocalDate reservationDate, LocalTime reservationTime, Integer storeId);

}
