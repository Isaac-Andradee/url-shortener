package com.isaacandrade.keygeneratorservice.unit_tests.keygen.application.base62.core;

import com.isaacandrade.keygeneratorservice.keygen.application.base62.core.Base62Encoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Base62EncoderTest {

    @Test
    void encode_convertsLongToBase62String() {
        long input = 123456789012345L;
        long LENGTH = 7;
        String result = Base62Encoder.encode(input);
        assertNotNull(result);
        assertEquals(LENGTH, result.length());
        assertTrue(result.matches("[0-9A-Za-z]+"));
    }

    @Test
    void encode_masksInputBeforeEncoding() {
        long input = -1L; // all bits 1
        long MASK =(1L << (7 * 7)) - 1;
        String result1 = Base62Encoder.encode(input);
        String result2 = Base62Encoder.encode(MASK);
        assertEquals(result1, result2);
    }
}
