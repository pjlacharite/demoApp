package com.demoapp.exception;

public class SubscriptionEventException extends Exception {
    private String errorCode;
    private String errorMessage;

    public SubscriptionEventException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
