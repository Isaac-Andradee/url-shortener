package com.isaacandrade.resolverservice.integration_tests.infra.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaacandrade.resolverservice.exception.KeyNotFoundException;
import com.isaacandrade.resolverservice.infra.router.ResolverController;
import com.isaacandrade.resolverservice.resolver.application.ResolverUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ResolverControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResolverUseCase resolverUseCase;

    @Test
    void resolve_ReturnsRedirect() throws Exception {
        String shortKey = "abc123";
        String longUrl = "https://example.com";
        when(resolverUseCase.resolve(shortKey)).thenReturn(longUrl);
        mockMvc.perform(get("/" + shortKey))
                .andExpect(status().isTemporaryRedirect())
                .andExpect(redirectedUrl(longUrl));
    }

    @Test
    void resolve_WhenKeyNotFound_Returns404() throws Exception {
        String shortKey = "notfound";
        String errorMessage = "Chave não encontrada";
        when(resolverUseCase.resolve(shortKey)).thenThrow(new KeyNotFoundException(errorMessage));
        mockMvc.perform(get("/" + shortKey))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value(errorMessage));
    }
}
