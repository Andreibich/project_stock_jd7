package com.htp.controller.messages;

public class ErrorMessage {
    private Long errorCode;

    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(Long errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
