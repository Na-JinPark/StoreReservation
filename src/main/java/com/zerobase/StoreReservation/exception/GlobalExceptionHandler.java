package com.zerobase.StoreReservation.exception;

import com.zerobase.StoreReservation.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ErrorResponse handleAccountException(UserException e){
        log.error("{} is occured.", e.getErrorCode());

        return  new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }
}
