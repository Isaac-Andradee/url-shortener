package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.config;

import com.isaacandrade.urlshortenerservice.config.ShortenerProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ShortenerPropertiesTest {

    @Mock
    private ShortenerProperties shortenerProperties;

    @Test
    void getDomainShouldReturnSetValue() {
        shortenerProperties = new ShortenerProperties();
        shortenerProperties.setDomain("http://localhost");
        assertEquals("http://localhost", shortenerProperties.getDomain());
    }

    @Test
    void toStringShouldContainDomain() {
        shortenerProperties = new ShortenerProperties();
        shortenerProperties.setDomain("http://test.com");
        String result = shortenerProperties.toString();
        assertTrue(result.contains("http://test.com"));
    }
}