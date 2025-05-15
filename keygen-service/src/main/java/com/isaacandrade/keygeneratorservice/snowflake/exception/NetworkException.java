package com.isaacandrade.keygeneratorservice.snowflake.exception;

public class NetworkException extends RuntimeException {
    public NetworkException() {super("Network Error!");}
    public NetworkException(String message) {
        super(message);
    }
}
