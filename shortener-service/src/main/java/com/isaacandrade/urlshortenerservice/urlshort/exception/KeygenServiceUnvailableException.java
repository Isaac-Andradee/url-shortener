package com.isaacandrade.urlshortenerservice.urlshort.exception;

public class KeygenServiceUnvailableException extends RuntimeException {
    public KeygenServiceUnvailableException() {super("Keygen Service Is Unavailable");}
    public KeygenServiceUnvailableException(String message) {super(message);}
}
