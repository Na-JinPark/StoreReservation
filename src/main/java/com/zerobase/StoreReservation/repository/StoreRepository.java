package com.zerobase.StoreReservation.repository;

import com.zerobase.StoreReservation.domain.Store;
import com.zerobase.StoreReservation.domain.id.StoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, StoreId> {
}
