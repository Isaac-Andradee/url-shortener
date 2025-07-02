/*
 * Copyright 2025 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isaacandrade.keygeneratorservice.keygen.application.base62.core;

import static com.isaacandrade.keygeneratorservice.keygen.application.base62.utils.Base62Constants.*;

/**
 * Base62Encoder is a utility class for encoding long values into Base62 strings.
 * It is used to convert unique identifiers (like snowflake IDs) into a more compact and URL-friendly format.
 * The encoding uses a custom Base62 character set defined in Base62Constants.
 *
 * @author Isaac Andrade
 */
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
