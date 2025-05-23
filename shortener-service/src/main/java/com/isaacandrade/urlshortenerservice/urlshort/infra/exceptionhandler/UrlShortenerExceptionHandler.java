package com.isaacandrade.urlshortenerservice.urlshort.infra.exceptionhandler;

import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasInvalidFormatException;
import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasNotAvailableException;
import com.isaacandrade.urlshortenerservice.urlshort.exception.KeygenServiceUnvailableException;
import com.isaacandrade.urlshortenerservice.urlshort.exception.message.AliasInvalidFormatMessage;
import com.isaacandrade.urlshortenerservice.urlshort.exception.message.AliasNotAvailableMessage;
import com.isaacandrade.urlshortenerservice.urlshort.exception.message.KeygenServiceUnavailableMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UrlShortenerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AliasNotAvailableException.class)
    public ResponseEntity<AliasNotAvailableMessage> aliasNotAvailableHandler(AliasNotAvailableException e) {
        AliasNotAvailableMessage response = new AliasNotAvailableMessage(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(KeygenServiceUnvailableException.class)
    public ResponseEntity<KeygenServiceUnavailableMessage> keygenServiceUnvailableHandler(KeygenServiceUnvailableException e) {
        KeygenServiceUnavailableMessage response = new KeygenServiceUnavailableMessage(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<AliasInvalidFormatMessage> aliasInvalidFormatHandler(AliasInvalidFormatException e) {
        AliasInvalidFormatMessage response = new AliasInvalidFormatMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
