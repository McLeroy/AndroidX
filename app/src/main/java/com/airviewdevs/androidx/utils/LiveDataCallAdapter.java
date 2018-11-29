package com.airviewdevs.androidx.utils;

import android.text.TextUtils;

import com.airviewdevs.androidx.ApiResponse;
import com.airviewdevs.androidx.api.exception.Resolvable;
import com.airviewdevs.androidx.api.exception.ResolvableApiException;

import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private final Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @NonNull
    @Override
    public Type responseType() {
        return responseType;
    }

    @NonNull
    @Override
    public LiveData<ApiResponse<R>> adapt(@NonNull Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            private  boolean started = false;

            @Override
            public void onActive() {
                super.onActive();
                DebugUtils.debug(LiveDataCallAdapter.class, "Loading data");
                call.enqueue(new Callback<R>() {
                    @Override
                    public void onResponse(@NonNull Call<R> call, @NonNull Response<R> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            postValue(ApiResponse.create(response));
                        }else {
                            try {
                                String message = response.errorBody() != null ? response.errorBody().string() : response.message();
                                Resolvable resolvable = Resolvable.fromJson(message, Resolvable.class);
                                resolvable.setStatusCode(response.code());
                                if (TextUtils.isEmpty(resolvable.getMessage())) throw new ResolvableApiException(message, 500);
                                throw new ResolvableApiException(resolvable.getMessage(), resolvable.getStatusCode());
                            }catch (Exception e) {
                                e.printStackTrace();
                                ResolvableApiException ex = (e instanceof  ResolvableApiException)
                                        ? (ResolvableApiException)e
                                        : new ResolvableApiException(e.getMessage(), 500);
                                postValue(ApiResponse.create(ex));
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<R> call, @NonNull Throwable t) {
                        postValue(ApiResponse.create(new ResolvableApiException(t.getMessage(), 400)));
                    }
                });
            }
        };
    }
}
