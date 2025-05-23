package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.infra.exceptionhandler;

import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasInvalidFormatException;
import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasNotAvailableException;
import com.isaacandrade.urlshortenerservice.urlshort.exception.KeygenServiceUnvailableException;
import com.isaacandrade.urlshortenerservice.urlshort.exception.message.AliasInvalidFormatMessage;
import com.isaacandrade.urlshortenerservice.urlshort.exception.message.AliasNotAvailableMessage;
import com.isaacandrade.urlshortenerservice.urlshort.exception.message.KeygenServiceUnavailableMessage;
import com.isaacandrade.urlshortenerservice.urlshort.infra.exceptionhandler.UrlShortenerExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UrlShortenerExceptionHandlerTest {

    @InjectMocks
    private UrlShortenerExceptionHandler urlShortenerExceptionHandler;

    @Test
    void shouldHandleAliasNotAvailableException() {
        AliasNotAvailableException ex = new AliasNotAvailableException("Alias Not Available");
        ResponseEntity<AliasNotAvailableMessage> response = urlShortenerExceptionHandler.aliasNotAvailableHandler(ex);
        assertEquals(409, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
        assertEquals(ex.getMessage(), response.getBody().getMessage());
    }

    @Test
    void shouldHandleKeygenServiceIsUnavailableException() {
        KeygenServiceUnvailableException ex = new KeygenServiceUnvailableException("Keygen Service Is Unavailable");
        ResponseEntity<KeygenServiceUnavailableMessage> response = urlShortenerExceptionHandler.keygenServiceUnvailableHandler(ex);
        assertEquals(503, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), response.getBody().getStatus());
        assertEquals(ex.getMessage(), response.getBody().getMessage());
    }

    @Test
    void shouldHandleAliasInvalidFormatException() {
       AliasInvalidFormatException ex = new AliasInvalidFormatException("Alias Invalid Format");
       ResponseEntity<AliasInvalidFormatMessage> response = urlShortenerExceptionHandler.aliasInvalidFormatHandler(ex);
       assertEquals(400, response.getStatusCode().value());
       assertNotNull(response.getBody());
       assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
       assertEquals(ex.getMessage(), response.getBody().getMessage());
    }
}
