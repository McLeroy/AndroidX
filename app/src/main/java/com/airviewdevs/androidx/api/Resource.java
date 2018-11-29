package com.airviewdevs.androidx.api;

import com.airviewdevs.androidx.api.exception.ResolvableApiException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Class that encapsulates loading state, response and data
 */
public final class Resource <T>{

    @NonNull private final Status status;
    @Nullable private final T data;
    @Nullable private final ResolvableApiException ex;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable ResolvableApiException ex) {
        this.status = status;
        this.data = data;
        this.ex = ex;
    }

    public static <T>Resource<T>loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    public static <T>Resource<T>success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T>Resource<T>error(@NonNull String message, int code) {
        return new Resource<>(Status.ERROR, null, new ResolvableApiException(message, code));
    }

    public static <T>Resource<T>error(@NonNull ResolvableApiException ex) {
        return new Resource<>(Status.ERROR, null, ex);
    }



    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public ResolvableApiException getEx() {
        return ex;
    }

    public enum Status {LOADING, SUCCESS, ERROR}
}
