package com.airviewdevs.androidx.todos;

import com.airviewdevs.androidx.App;
import com.airviewdevs.androidx.api.Resource;
import com.airviewdevs.androidx.models.Todo;
import com.airviewdevs.androidx.utils.NetworkResourceLiveData;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class TodoViewModel extends ViewModel {
    private NetworkResourceLiveData<List<Todo>>todosLiveData;
    public TodoViewModel() {

    }

    public NetworkResourceLiveData<Resource<List<Todo>>>getTodos(){
        if (todosLiveData == null)
            todosLiveData = new NetworkResourceLiveData<>();
        App.getApiService().getTodos(1).observe();
    }
}
