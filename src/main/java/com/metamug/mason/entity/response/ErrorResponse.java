package com.metamug.mason.entity.response;

import java.util.UUID;

public class ErrorResponse {
    String errorId;
    int status = 512;
    String error = "Internal Server Error";
    String message = "API Error. Please contact your API administrator." ;

    public ErrorResponse() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        long hash = UUID.nameUUIDFromBytes(timestamp.getBytes()).getMostSignificantBits();
        errorId = String.valueOf(Math.abs(hash));
    }

    public ErrorResponse(String errorId, int status, String error, String message) {
        this();
        this.errorId = errorId;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(int status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
