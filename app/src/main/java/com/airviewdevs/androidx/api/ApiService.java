package com.airviewdevs.androidx.api;

import com.airviewdevs.androidx.ApiResponse;
import com.airviewdevs.androidx.models.Todo;
import com.airviewdevs.androidx.utils.NetworkResourceLiveData;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    String ENDPOINT = "https://jsonplaceholder.typicode.com/";

    @GET("todos")
    NetworkResourceLiveData<ApiResponse<List<Todo>>> getTodos(long id);
}
