package com.airviewdevs.androidx.respositories;

import com.airviewdevs.androidx.ApiResponse;
import com.airviewdevs.androidx.App;
import com.airviewdevs.androidx.AppExecutors;
import com.airviewdevs.androidx.api.NetworkBasedResponse;
import com.airviewdevs.androidx.api.Resource;
import com.airviewdevs.androidx.models.Todo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TodoRepository extends BaseRepository{
    private static TodoRepository instance = new TodoRepository();
    public static TodoRepository get() {
        return instance;
    }


    public LiveData<Resource<List<Todo>>> getTodos() {
        return new NetworkBasedResponse<List<Todo>, List<Todo>>(getAppExecutors()) {
            @Override
            public LiveData<ApiResponse<List<Todo>>> createCall() {
                return App.getApiService().getTodos();
            }

            @Override
            public LiveData<List<Todo>> convertToLiveData(List<Todo> todos) {
                return convertResultToLiveData(todos);
            }


        }.asLiveData();
    }

    public LiveData<Resource<Todo>>getTodo(long id) {
        return new NetworkBasedResponse<Todo, Todo>(getAppExecutors()) {

            @Override
            public LiveData<ApiResponse<Todo>> createCall() {
                return App.getApiService().getTodo(id);
            }

            @Override
            public LiveData<Todo> convertToLiveData(Todo todo) {
                return convertResultToLiveData(todo);
            }
        }.asLiveData();
    }

}
