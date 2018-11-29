package com.airviewdevs.androidx.todos;

import com.airviewdevs.androidx.ApiResponse;
import com.airviewdevs.androidx.App;
import com.airviewdevs.androidx.api.Resource;
import com.airviewdevs.androidx.models.Todo;
import com.airviewdevs.androidx.utils.NetworkResourceLiveData;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class TodoViewModel extends ViewModel {
    private MediatorLiveData<Resource<Todo>>mediatorLiveData;
    private NetworkResourceLiveData<List<Todo>>todosLiveData;
    public TodoViewModel() {
        mediatorLiveData = new MediatorLiveData<>();
    }

    public NetworkResourceLiveData<Resource<List<Todo>>>getTodos(){
        if (todosLiveData == null)
            todosLiveData = new NetworkResourceLiveData<>();
        mediatorLiveData.addSource(App.getApiService().getTodos(1), new Observer<ApiResponse<List<Todo>>>() {
            @Override
            public void onChanged(ApiResponse<List<Todo>> listApiResponse) {
                if (listApiResponse instanceof ApiResponse.ApiSuccessResponse) {
                    ApiResponse.ApiSuccessResponse successResponse = (ApiResponse.ApiSuccessResponse)listApiResponse;
                    mediatorLiveData.setValue(Resource.success(successResponse.getBody()));
                }
            }
        });
    }
}
