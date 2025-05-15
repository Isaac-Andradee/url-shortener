package com.isaacandrade.urlshortenerservice.urlshort.exception.message;

public class AliasNotAvailableMessage {
    private int status;
    private String message;

    public AliasNotAvailableMessage(int status, String message) {
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
