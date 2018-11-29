package com.airviewdevs.androidx.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {
    @NonNull public final Status status;
    @Nullable public final T data;
    @Nullable public final Exception ex;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable Exception ex) {
        this.status = status;
        this.data = data;
        this.ex = ex;
    }

    public static <T>Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T>Resource<T>error(@NonNull Exception ex, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, ex);
    }

    public static <T>Resource<T>loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    public enum Status {SUCCESS, ERROR, LOADING}
}
