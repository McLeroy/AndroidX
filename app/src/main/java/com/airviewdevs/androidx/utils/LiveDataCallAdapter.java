package com.airviewdevs.androidx.utils;

import com.airviewdevs.androidx.ApiResponse;

import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, NetworkResourceLiveData<ApiResponse<R>>> {
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
    public NetworkResourceLiveData<ApiResponse<R>> adapt(@NonNull Call<R> call) {
        return new NetworkResourceLiveData<ApiResponse<R>>() {
            private  boolean started = false;

            @Override
            protected void loadData() {
                super.loadData();
                call.enqueue(new Callback<R>() {
                    @Override
                    public void onResponse(@NonNull Call<R> call, @NonNull Response<R> response) {
                        postValue(ApiResponse.create(response));
                    }

                    @Override
                    public void onFailure(@NonNull Call<R> call, @NonNull Throwable t) {
                        postValue(ApiResponse.create(new Exception(t)));
                    }
                });
            }
        };
    }
}
