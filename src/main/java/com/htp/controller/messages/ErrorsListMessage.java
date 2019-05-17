package com.htp.controller.messages;

import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsListMessage {
    private Long errorCode;

    private List<FieldError> errors;

    private String message;

    public ErrorsListMessage() {
    }

    public ErrorsListMessage(List<FieldError> errors, String message) {
        this.errors = errors;
        this.message = message;
    }

    public ErrorsListMessage(Long errorCode, List<FieldError> errors, String message) {
        this.errorCode = errorCode;
        this.errors = errors;
        this.message = message;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }
}
