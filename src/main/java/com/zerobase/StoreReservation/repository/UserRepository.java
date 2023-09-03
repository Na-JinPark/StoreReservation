package com.zerobase.StoreReservation.repository;

import com.zerobase.StoreReservation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByNickName(String nickName);
}
