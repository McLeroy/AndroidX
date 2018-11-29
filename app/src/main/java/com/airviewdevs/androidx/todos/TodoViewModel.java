package com.airviewdevs.androidx.todos;

import com.airviewdevs.androidx.api.Resource;
import com.airviewdevs.androidx.models.Todo;
import com.airviewdevs.androidx.respository.TodoRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodoViewModel extends ViewModel {
    private TodoRepository todoRepository;

    public TodoViewModel() {
        todoRepository = TodoRepository.get();
    }

    public LiveData<Resource<List<Todo>>>getTodos(){
        return todoRepository.getTodos();
    }
}
