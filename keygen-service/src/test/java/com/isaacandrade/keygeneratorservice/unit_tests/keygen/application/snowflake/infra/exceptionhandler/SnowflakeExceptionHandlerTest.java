package com.isaacandrade.keygeneratorservice.unit_tests.keygen.application.snowflake.infra.exceptionhandler;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.NetworkException;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.RegressiveClockException;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.message.NetworkErrorMessage;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.message.RegressiveClockErrorMessage;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.exceptionhandler.SnowflakeExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SnowflakeExceptionHandlerTest {
    private SnowflakeExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new SnowflakeExceptionHandler();
    }

    @Test
    void regressiveClockExceptionHandler_returns503WithMessage() {
        RegressiveClockException ex = new RegressiveClockException("Clock Moved Backwards!");
        ResponseEntity<RegressiveClockErrorMessage> response = handler.regressiveClockExceptionHandler(ex);
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(503, response.getBody().getStatus());
        assertEquals("Clock Moved Backwards!", response.getBody().getMessage());
    }

    @Test
    void networkExceptionHandler_returns500WithMessage() {
        NetworkException ex = new NetworkException("Connection failed!");
        ResponseEntity<NetworkErrorMessage> response = handler.networkExceptionHandler(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("Connection failed!", response.getBody().getMessage());
    }
}
