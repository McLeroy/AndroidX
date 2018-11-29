package com.airviewdevs.androidx.api;

import com.airviewdevs.androidx.ApiResponse;
import com.airviewdevs.androidx.models.Todo;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    String ENDPOINT = "https://jsonplaceholder.typicode.com/";

    @GET("todos")
    LiveData<ApiResponse<List<Todo>>> getTodos();

    @GET("todos/{id}")
    LiveData<ApiResponse<Todo>>getTodo(@Path("id") long id);
}
