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
package com.isaacandrade.keygeneratorservice.keygen.infra.router;

import com.isaacandrade.keygeneratorservice.keygen.application.KeyGenUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * Controller for generating unique keys.
 * This controller handles requests to generate a unique key and returns it in the response.
 *
 * @author Isaac Andrade
 */
@RestController
@RequestMapping("/generate")
public class KeyGenController {

    private final KeyGenUseCase keyGenUseCase;

    public KeyGenController(KeyGenUseCase keyGenUseCase) {
        this.keyGenUseCase = keyGenUseCase;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> generate() {
        String uniqueKey = keyGenUseCase.generateUniqueKey();
        return ResponseEntity.ok(Collections.singletonMap("shortKey", uniqueKey));
    }
}
