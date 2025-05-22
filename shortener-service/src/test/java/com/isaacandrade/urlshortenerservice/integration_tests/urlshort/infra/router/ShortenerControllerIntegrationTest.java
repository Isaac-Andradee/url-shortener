package com.isaacandrade.urlshortenerservice.integration_tests.urlshort.infra.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaacandrade.urlshortenerservice.integration_tests.urlshort.config.BaseIntegrationTest;
import com.isaacandrade.urlshortenerservice.urlshort.application.dto.ShortenRequest;
import com.isaacandrade.urlshortenerservice.urlshort.application.dto.ShortenResponse;
import com.isaacandrade.urlshortenerservice.urlshort.infra.router.ShortenerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ShortenerControllerIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShortenerController shortenerController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnShortenedUrlWhenAliasIsProvided() throws Exception {
        ShortenRequest request = new ShortenRequest("https://example.com", "abc123");
        ShortenResponse response = new ShortenResponse("https://short.url/abc123");
        when(shortenerController.shortenUrl(request)).thenReturn(ResponseEntity.ok(response));
        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").exists());
    }

    @Test
    void shouldReturnShortenedUrlWhenAliasIsNotProvided() throws Exception{
        ShortenRequest request = new ShortenRequest("https://example.com", null);
        ShortenResponse response = new ShortenResponse("https://short.url/xyz789");
        when(shortenerController.shortenUrl(request)).thenReturn(ResponseEntity.ok(response));
        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").exists());
    }
}
