package com.zerobase.StoreReservation.repository;

import com.zerobase.StoreReservation.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    Optional<Store> findByStoreId(Integer storeId);
}
