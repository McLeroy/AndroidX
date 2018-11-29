package com.airviewdevs.androidx.api.exception;

/**
 * Created by mcleroy on 3/21/2018.
 */

public class ResolvableApiException extends Exception{
    private int statusCode;
    private String message;

    public ResolvableApiException(String message) {
        super(message);
        this.message = message;
        statusCode = 500;
    }

    public ResolvableApiException(String message, int statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

    public ResolvableApiException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }


    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
