/*
 * Copyright 2025 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/** * Global exception handler for URL shortener service.
 * This class handles exceptions related to alias availability, format validation, and service unavailability.
 * It returns appropriate HTTP responses with error messages.
 *
 * @author Isaac Andrade
 */
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
