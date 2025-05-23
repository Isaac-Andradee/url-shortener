package com.isaacandrade.resolverservice.infra.router;

import com.isaacandrade.resolverservice.resolver.annotations.ResolverUrlOperation;
import com.isaacandrade.resolverservice.resolver.application.ResolverUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/")
public class ResolverController {

    private final ResolverUseCase resolverUseCase;

    public ResolverController(ResolverUseCase resolverUseCase) {
        this.resolverUseCase = resolverUseCase;
    }

    @GetMapping("/{shortKey}")
    @ResolverUrlOperation
    public ResponseEntity<Void> resolve(@PathVariable String shortKey) {
        String longUrl = resolverUseCase.resolve(shortKey);
        return ResponseEntity.status(307)
                .location(URI.create(longUrl))
                .build();
    }
}
