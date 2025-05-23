package com.isaacandrade.urlshortenerservice.urlshort.exception;

public class AliasInvalidFormatException extends RuntimeException {
    public AliasInvalidFormatException() {super("Invalid Alias Format");}
    public AliasInvalidFormatException(String message) {
        super(message);
    }
}
