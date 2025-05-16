package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception;

public class NetworkException extends RuntimeException {
    public NetworkException() {super("Network Error!");}
    public NetworkException(String message) {
        super(message);
    }
}
