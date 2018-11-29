package com.airviewdevs.androidx.todos;

import com.airviewdevs.androidx.ApiResponse;
import com.airviewdevs.androidx.App;
import com.airviewdevs.androidx.api.NetworkBasedResponse;
import com.airviewdevs.androidx.api.Resource;
import com.airviewdevs.androidx.models.Todo;
import com.airviewdevs.androidx.respositories.TodoRepository;
import com.airviewdevs.androidx.utils.DebugUtils;

import java.util.List;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoViewModel extends ViewModel {
    private final TodoRepository todoRepository;

    public TodoViewModel() {
        todoRepository = TodoRepository.get();
    }

    public LiveData<Resource<List<Todo>>> getTodos() {
        return todoRepository.getTodos();
    }

    public LiveData<Resource<Todo>>getTodo(long id) {
        return todoRepository.getTodo(id);
    }

}
