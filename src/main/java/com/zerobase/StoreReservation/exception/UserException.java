package com.zerobase.StoreReservation.exception;

import com.zerobase.StoreReservation.type.ErrorCode;
import lombok.*;
@Getter
@Setter
// @AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public UserException(ErrorCode errorcode){
        this.errorCode = errorcode;
        this.errorMessage = errorCode.getDescription();
    }

    public UserException(ErrorCode errorcode, String errorMessage){
        this.errorCode = errorcode;
        this.errorMessage = errorMessage;
    }
}