package com.isaacandrade.resolverservice.infra.exception_handler;

import com.isaacandrade.resolverservice.exception.KeyNotFoundException;
import com.isaacandrade.resolverservice.exception.message.KeyNotFoundMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResolverExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(KeyNotFoundException.class)
    public ResponseEntity<KeyNotFoundMessage> keyNotFoundHandler(KeyNotFoundException e) {
        KeyNotFoundMessage response = new KeyNotFoundMessage(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
