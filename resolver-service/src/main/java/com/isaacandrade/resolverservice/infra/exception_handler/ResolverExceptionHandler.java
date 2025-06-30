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
