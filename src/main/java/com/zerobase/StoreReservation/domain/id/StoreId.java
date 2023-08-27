package com.zerobase.StoreReservation.domain.id;
import com.zerobase.StoreReservation.domain.User;

import java.io.Serializable;
import java.math.BigInteger;

public class StoreId implements Serializable {
    private User userId;
    private BigInteger storeId;

}