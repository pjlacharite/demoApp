package com.demoapp.controller.response;

public class SubscriptionJsonResponse {
    public static final String ERROR_CODE_GENERAL = "Subscription error";
    public static final String ERROR_MESSAGE_GENERAL = "An error was encountered while processing your subscription event";
    private String success = "false";
    private String accountIdentifier;
    private String errorCode;
    private String message;

    public SubscriptionJsonResponse() {
    }

    public static SubscriptionJsonResponse getSuccessResponse(String accountIdentifier){
        SubscriptionJsonResponse subscriptionJsonResponse = new SubscriptionJsonResponse();
        subscriptionJsonResponse.success = "true";
        subscriptionJsonResponse.accountIdentifier = accountIdentifier;
        return subscriptionJsonResponse;
    }

    public static SubscriptionJsonResponse getFailureResponse(String message, String errorCode){
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
