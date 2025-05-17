package com.isaacandrade.keygeneratorservice.integration_tests.keygen.infra.router;

import com.isaacandrade.keygeneratorservice.keygen.application.KeyGenUseCase;
import com.isaacandrade.keygeneratorservice.keygen.infra.router.KeyGenController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KeyGenController.class)
@ActiveProfiles("test")
public class KeygenControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private KeyGenUseCase keyGenUseCase;

    @Test
    void generate_returnsShortKey() throws Exception {
        String expectedKey = "abc123XYZ";
        when(keyGenUseCase.generateUniqueKey()).thenReturn(expectedKey);
        mockMvc.perform(get("/generate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortKey").value(expectedKey));
    }
}
