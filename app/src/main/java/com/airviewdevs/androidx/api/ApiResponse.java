package com.airviewdevs.androidx.api;

import com.airviewdevs.androidx.api.exception.ResolvableApiException;

import retrofit2.Response;

public class ApiResponse<T> {

    public static <T>ApiErrorResponse<T>create(Exception ex) {
        return new ApiErrorResponse<>(ex);
    }

    public static <T>ApiResponse<T>create(Response<T>response) {
        if (response.isSuccessful()) {
            T responseBody = response.body();
            if (responseBody == null || response.code() == 204) {
                return new ApiEmptyResponse<>();
            }else {
                return new ApiSuccessResponse<>(responseBody);
            }
        }else {
            try {
                String message = response.errorBody() != null ? response.errorBody().string() : response.message();
                throw new ResolvableApiException(message, response.code());
            }catch (Exception ex) {
                ex.printStackTrace();
                return new ApiErrorResponse<>(ex);
            }
        }
    }


    public static final class ApiEmptyResponse<T> extends ApiResponse<T> {

    }

    public static final class ApiSuccessResponse<T> extends ApiResponse<T> {
        private T body;

        public ApiSuccessResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }
    }

    public static final class ApiErrorResponse<T> extends ApiResponse<T> {
        private Exception ex;

        public ApiErrorResponse(Exception ex) {
            this.ex = ex;
        }

        public Exception getEx() {
            return ex;
        }
    }
}
