package com.isaacandrade.resolverservice.unit_tests.infra.exception_handler;

import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.resolverservice.exception.KeyNotFoundException;
import com.isaacandrade.resolverservice.exception.message.KeyNotFoundMessage;
import com.isaacandrade.resolverservice.infra.exception_handler.ResolverExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResolverExceptionHandlerTest {

    @Mock
    private ResolverExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ResolverExceptionHandler();
    }

    @Test
    void keyNotFoundHandler_ReturnsNotFound() {
        String message = "Chave não encontrada";
        KeyNotFoundException ex = new KeyNotFoundException(message);
        ResponseEntity<KeyNotFoundMessage> response = handler.keyNotFoundHandler(ex);
        assertEquals(404, response.getStatusCode().value());
        KeyNotFoundMessage body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.getStatus());
        assertEquals(message, body.getMessage());
    }
}
