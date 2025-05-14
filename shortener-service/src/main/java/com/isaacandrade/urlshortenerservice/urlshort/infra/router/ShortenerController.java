package com.isaacandrade.urlshortenerservice.url.infra.router;

import com.isaacandrade.urlshortenerservice.url.application.dto.ShortenRequest;
import com.isaacandrade.urlshortenerservice.url.application.ShortenerUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shorten")
public class ShortenerController {

    private final ShortenerUseCase shortenerUseCase;

    public ShortenerController(ShortenerUseCase shortenerUseCase) {
        this.shortenerUseCase = shortenerUseCase;
    }

    @PostMapping
    public ResponseEntity<String> shortenUrl(@RequestBody ShortenRequest request) {
        String shortUrl = shortenerUseCase.shorten(request);
        return ResponseEntity.ok(shortUrl);
    }
}
