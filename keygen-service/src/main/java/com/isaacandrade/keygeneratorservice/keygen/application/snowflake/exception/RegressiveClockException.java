package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception;

public class RegressiveClockException extends RuntimeException {
    public RegressiveClockException() {super("Incapable To Generate Unique IDs");}
    public RegressiveClockException(String message) {
        super(message);
    }
}
