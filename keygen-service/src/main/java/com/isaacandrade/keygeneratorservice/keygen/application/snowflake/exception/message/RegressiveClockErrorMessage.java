package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.message;

public class RegressiveClockErrorMessage {
    private int status;
    private String message;

    public RegressiveClockErrorMessage(int status, String message) {
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
