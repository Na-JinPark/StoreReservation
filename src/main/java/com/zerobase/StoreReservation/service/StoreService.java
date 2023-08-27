package com.zerobase.StoreReservation.service;

import com.zerobase.StoreReservation.domain.Store;
import com.zerobase.StoreReservation.dto.StoreDto;
import com.zerobase.StoreReservation.dto.StoreRegist;
import com.zerobase.StoreReservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreDto storeRegist(StoreRegist.Request request){
       StoreDto store = null;

        return  store;
    }

}
