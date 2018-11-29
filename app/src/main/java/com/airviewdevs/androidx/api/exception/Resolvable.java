package com.airviewdevs.androidx.api.exception;


import com.airviewdevs.androidx.models.BaseModel;

public class Resolvable extends BaseModel {
    private String message;
    private String status;
    private int statusCode;
    private transient ResolvableApiException ex;

    public Resolvable() {
    }

    public Resolvable(ResolvableApiException ex) {
        this.ex = ex;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public ResolvableApiException getEx() {
        return ex;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
