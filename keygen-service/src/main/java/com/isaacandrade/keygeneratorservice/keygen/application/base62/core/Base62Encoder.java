package com.isaacandrade.keygeneratorservice.keygen.application.base62.core;

import static com.isaacandrade.keygeneratorservice.keygen.application.base62.utils.Base62Constants.*;

public class Base62Encoder {
    public static String encode(long snowflakeId) {
        long uniqueId = snowflakeId & MASK;
        char[] buf = new char[LENGTH];
        for (int i = LENGTH - 1; i >= 0; i--) {
            buf[i] = BASE62.charAt((int)(uniqueId % 62));
            uniqueId /= 62;
        }
        return new String(buf);
    }
}
