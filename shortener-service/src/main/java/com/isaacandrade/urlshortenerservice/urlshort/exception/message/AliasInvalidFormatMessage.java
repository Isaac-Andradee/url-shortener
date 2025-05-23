package com.isaacandrade.urlshortenerservice.urlshort.exception.message;

public class AliasInvalidFormatMessage {
    int status;
    String message;

    public AliasInvalidFormatMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
