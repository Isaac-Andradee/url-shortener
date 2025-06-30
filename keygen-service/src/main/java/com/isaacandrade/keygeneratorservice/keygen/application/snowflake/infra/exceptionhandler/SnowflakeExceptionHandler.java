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
package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.exceptionhandler;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.NetworkException;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.RegressiveClockException;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.message.NetworkErrorMessage;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.message.RegressiveClockErrorMessage;
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
