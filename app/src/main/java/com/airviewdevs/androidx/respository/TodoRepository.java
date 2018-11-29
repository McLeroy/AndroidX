package com.airviewdevs.androidx.respository;

import com.airviewdevs.androidx.App;
import com.airviewdevs.androidx.AppExecutors;
import com.airviewdevs.androidx.api.ApiResponse;
import com.airviewdevs.androidx.api.ApiService;
import com.airviewdevs.androidx.api.Resource;
import com.airviewdevs.androidx.models.Todo;
import com.airviewdevs.androidx.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TodoRepository {
    private static TodoRepository instance = new TodoRepository();
    private AppExecutors appExecutors;
    private ApiService apiService;

    public static TodoRepository get() {
        return instance;
    }

    private TodoRepository() {
        apiService = App.getApiService();
        appExecutors = AppExecutors.get();
    }

    public LiveData<Resource<List<Todo>>> getTodos() {
        final List<Todo>databaseTodos = new ArrayList<>();
        return new NetworkBoundResource<List<Todo>, List<Todo>>(appExecutors) {
            @Override
            protected LiveData<List<Todo>> loadFromDb() {
                MutableLiveData<List<Todo>>liveData = new MutableLiveData<>();
                liveData.setValue(databaseTodos);
                return liveData;
            }

            @Override
            protected LiveData<ApiResponse<List<Todo>>> createCall() {
                DebugUtils.debug(TodoRepository.class, "Creating call");
                return apiService.getTodos();
            }

            @Override
            protected void saveCallResult(List<Todo> todos) {
                DebugUtils.debug(TodoRepository.class, "Saving call result");
                databaseTodos.clear();
                databaseTodos.addAll(todos);
            }

            @Override
            protected LiveData<List<Todo>> convertToLiveData(@Nullable List<Todo> todos) {
                MutableLiveData<List<Todo>>convertedTodos = new MutableLiveData<>();
                convertedTodos.setValue(todos);
                return convertedTodos;
            }
        }.asLiveData();
    }
}
