package com.demoapp.controller.response;

public class SubscriptionJsonResponse {
    public static final String ERROR_CODE_USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static final String ERROR_CODE_USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String ERROR_CODE_ACCOUNT_NOT_FOUND = "ACCOUNT_NOT_FOUND";
    public static final String ERROR_CODE_MAX_USERS_REACHED = "MAX_USERS_REACHED";
    public static final String ERROR_CODE_UNAUTHORIZED = "UNAUTHORIZED";
    public static final String ERROR_CODE_OPERATION_CANCELED = "OPERATION_CANCELED";
    public static final String ERROR_CODE_CONFIGURATION_ERROR = "CONFIGURATION_ERROR";
    public static final String ERROR_CODE_INVALID_RESPONSE = "INVALID_RESPONSE";
    public static final String ERROR_CODE_FORBIDDEN = "FORBIDDEN";
    public static final String ERROR_CODE_BINDING_NOT_FOUND = "BINDING_NOT_FOUND";
    public static final String ERROR_CODE_TRANSPORT_ERROR = "TRANSPORT_ERROR";
    public static final String ERROR_CODE_UNKNOWN_ERROR = "UNKNOWN_ERROR";

    public static final String ERROR_MESSAGE_GENERAL = "An error was encountered while processing your subscription event";
    private String success = "false";
    private String accountIdentifier;
    private String errorCode;
    private String message;

    public SubscriptionJsonResponse() {
    }

    public static SubscriptionJsonResponse getSuccessResponse(String accountIdentifier) {
        SubscriptionJsonResponse subscriptionJsonResponse = new SubscriptionJsonResponse();
        subscriptionJsonResponse.success = "true";
        subscriptionJsonResponse.accountIdentifier = accountIdentifier;
        return subscriptionJsonResponse;
    }

    public static SubscriptionJsonResponse getFailureResponse(String message, String errorCode) {
        SubscriptionJsonResponse subscriptionJsonResponse = new SubscriptionJsonResponse();
        subscriptionJsonResponse.success = "false";
        subscriptionJsonResponse.message = message;
        subscriptionJsonResponse.errorCode = errorCode;
        return subscriptionJsonResponse;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}