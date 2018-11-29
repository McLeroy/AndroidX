package com.airviewdevs.androidx.todos;

import com.airviewdevs.androidx.ApiResponse;
import com.airviewdevs.androidx.App;
import com.airviewdevs.androidx.api.NetworkBoundResource;
import com.airviewdevs.androidx.api.Resource;
import com.airviewdevs.androidx.models.Todo;
import com.airviewdevs.androidx.utils.NetworkResourceLiveData;

import java.util.List;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class TodoViewModel extends ViewModel {
    private MediatorLiveData<Resource<List<Todo>>>mediatorLiveData;

    public TodoViewModel() {
        mediatorLiveData = new MediatorLiveData<>();
    }

    public NetworkResourceLiveData<Resource<List<Todo>>>getTodos(){

        return new NetworkBoundResource<List<Todo>, List<Todo>>() {

            @Override
            public NetworkResourceLiveData<Resource<List<Todo>>> asLiveData() {
                return null;
            }
        }.asLiveData();
    }

    @WorkerThread
    protected List<Todo> processResponse(ApiResponse.ApiSuccessResponse<List<Todo>>response) {
        return response.getBody();
    }


    protected LiveData<List<Todo>>convertToLiveData(List<Todo> requestType) {
        MutableLiveData<List<Todo>>liveData = new MutableLiveData<>();
        liveData.setValue(requestType);
        return liveData;
    }
}
