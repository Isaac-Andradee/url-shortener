package com.isaacandrade.keygeneratorservice.keygen.infra.router;

import com.isaacandrade.keygeneratorservice.keygen.application.KeyGenUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

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
