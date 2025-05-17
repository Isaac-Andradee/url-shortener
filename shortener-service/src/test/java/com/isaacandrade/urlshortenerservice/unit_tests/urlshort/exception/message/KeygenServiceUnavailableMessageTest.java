package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.exception.message;

import com.isaacandrade.urlshortenerservice.urlshort.exception.message.KeygenServiceUnavailableMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class KeygenServiceUnavailableMessageTest {

    @Mock
    private KeygenServiceUnavailableMessage message;

    @Test
    void constructorShouldSetFieldsCorrectly() {
        message = new KeygenServiceUnavailableMessage(503, "Keygen Service Is Unavailable");
        assertEquals(503, message.getStatus());
        assertEquals("Keygen Service Is Unavailable", message.getMessage());
    }

    @Test
    void settersShouldUpdateFieldsCorrectly() {
        message = new KeygenServiceUnavailableMessage(0, "");
        message.setStatus(500);
        message.setMessage("Internal Server Error");
        assertEquals(500, message.getStatus());
        assertEquals("Internal Server Error", message.getMessage());
    }
}
