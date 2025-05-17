package com.isaacandrade.keygeneratorservice.unit_tests.keygen.application.snowflake.exception.message;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.message.NetworkErrorMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class NetworkErrorMessageTests {

    @Mock
    NetworkErrorMessage message;

    @Test
    void constructorAndGetters_shouldWorkCorrectly() {
        message = new NetworkErrorMessage(500, "Network Error");
        assertEquals(500, message.getStatus());
        assertEquals("Network Error", message.getMessage());
    }

    @Test
    void setters_shouldUpdateFieldsCorrectly() {
        message = new NetworkErrorMessage(0, null);
        message.setStatus(500);
        message.setMessage("Internal Error");

        assertEquals(500, message.getStatus());
        assertEquals("Internal Error", message.getMessage());
    }
}
