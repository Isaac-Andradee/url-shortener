package com.isaacandrade.urlshortenerservice.urlshort.infra.router;

import com.isaacandrade.urlshortenerservice.urlshort.application.ShortenerUseCase;
import com.isaacandrade.urlshortenerservice.urlshort.application.dto.ShortenRequest;
import com.isaacandrade.urlshortenerservice.urlshort.application.dto.ShortenResponse;
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
    public ResponseEntity<ShortenResponse> shortenUrl(@RequestBody ShortenRequest request) {
        ShortenResponse shortUrl = shortenerUseCase.shorten(request);
        return ResponseEntity.ok(shortUrl);
    }
}
