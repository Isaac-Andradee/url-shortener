package com.isaacandrade.keygeneratorservice.keygen.application.base62.utils;

public class Base62Constants {
    public static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final int LENGTH = 7;
    public static final long MASK = (1L << (7 * 7)) - 1;
}
