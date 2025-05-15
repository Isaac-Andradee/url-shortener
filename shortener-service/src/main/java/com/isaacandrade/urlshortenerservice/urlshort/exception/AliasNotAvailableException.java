package com.isaacandrade.urlshortenerservice.urlshort.exception;

public class AliasNotAvailableException extends RuntimeException {
  public AliasNotAvailableException() {super("Alias Not Available");}
  public AliasNotAvailableException(String message) {
        super(message);
    }
}
