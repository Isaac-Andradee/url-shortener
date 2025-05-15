package com.isaacandrade.keygeneratorservice.snowflake.infra.exceptionhandler;

import com.isaacandrade.keygeneratorservice.snowflake.exception.NetworkException;
import com.isaacandrade.keygeneratorservice.snowflake.exception.RegressiveClockException;
import com.isaacandrade.keygeneratorservice.snowflake.exception.message.NetworkErrorMessage;
import com.isaacandrade.keygeneratorservice.snowflake.exception.message.RegressiveClockErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SnowflakeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegressiveClockException.class)
    public ResponseEntity<RegressiveClockErrorMessage> regressiveClockExceptionHandler(RegressiveClockException e) {
        RegressiveClockErrorMessage threatResponse = new RegressiveClockErrorMessage(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(threatResponse);
    }

    @ExceptionHandler(NetworkException.class)
    public ResponseEntity<NetworkErrorMessage> networkExceptionHandler(NetworkException e) {
        NetworkErrorMessage threatResponse = new NetworkErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }
}
