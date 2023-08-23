package com.zerobase.StoreReservation.repository;

import com.zerobase.StoreReservation.domain.USER;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<USER, Long> {
    Optional<USER> findByUserid(String AccountNumber);
}
