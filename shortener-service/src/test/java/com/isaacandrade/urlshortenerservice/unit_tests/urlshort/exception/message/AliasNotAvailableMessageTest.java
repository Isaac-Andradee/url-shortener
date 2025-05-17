package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.exception.message;

import com.isaacandrade.urlshortenerservice.urlshort.exception.message.AliasNotAvailableMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AliasNotAvailableMessageTest {

    @Mock
    AliasNotAvailableMessage message;

    @Test
    void constructorShouldSetFieldsCorrectly() {
        message = new AliasNotAvailableMessage(409, "Alias Not Available");
        assertEquals(409, message.getStatus());
        assertEquals("Alias Not Available", message.getMessage());
    }

    @Test
    void settersShouldUpdateFieldsCorrectly() {
        message = new AliasNotAvailableMessage(0, "");
        message.setStatus(400);
        message.setMessage("Invalid alias");
        assertEquals(400, message.getStatus());
        assertEquals("Invalid alias", message.getMessage());
    }
}
