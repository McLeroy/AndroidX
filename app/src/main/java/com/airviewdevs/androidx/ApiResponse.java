package com.airviewdevs.androidx;

import com.airviewdevs.androidx.api.exception.ResolvableApiException;

import androidx.annotation.NonNull;
import retrofit2.Response;

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
public class ApiResponse <T> {

    public static <T>ApiErrorResponse<T>create(ResolvableApiException ex) {
        return new ApiErrorResponse<T>(ex);
    }

    public static <T>ApiErrorResponse<T>create(String message, int code) {
        return new ApiErrorResponse<T>(message, code);
    }

    public static <T>ApiResponse<T>create(@NonNull Response<T>response) {
        return new ApiSuccessResponse<T>(response.body());
    }

    public static final class ApiSuccessResponse<T> extends ApiResponse<T>{
        private final T body;

        public ApiSuccessResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }
    }

    public static final class ApiErrorResponse<T> extends ApiResponse<T> {
        private final ResolvableApiException ex;

        public ApiErrorResponse(String message, int code) {
            this(new ResolvableApiException(message, code));
        }

        public ApiErrorResponse(ResolvableApiException ex) {
            this.ex = ex;
        }


        public ResolvableApiException getEx() {
            return ex;
        }
    }
}
