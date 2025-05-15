package com.isaacandrade.keygeneratorservice.snowflake.exception;

public class RegressiveClockException extends RuntimeException {
    public RegressiveClockException() {super("Incapable To Generate Unique IDs");}
    public RegressiveClockException(String message) {
        super(message);
    }
}
