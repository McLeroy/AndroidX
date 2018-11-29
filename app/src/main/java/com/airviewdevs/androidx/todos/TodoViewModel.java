package com.airviewdevs.androidx.todos;

import com.airviewdevs.androidx.ApiResponse;
import com.airviewdevs.androidx.App;
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
    private NetworkResourceLiveData<Resource<List<Todo>>>todosLiveData;
    public TodoViewModel() {
        mediatorLiveData = new MediatorLiveData<>();
    }

    public NetworkResourceLiveData<Resource<List<Todo>>>getTodos(){
        NetworkResourceLiveData<ApiResponse<List<Todo>>> networkCall = App.getApiService().getTodos();
        mediatorLiveData.addSource(networkCall, new Observer<ApiResponse<List<Todo>>>() {
            @Override
            public void onChanged(ApiResponse<List<Todo>> listApiResponse) {
                mediatorLiveData.removeSource(networkCall);
            }
        });
        mediatorLiveData.addSource(App.getApiService().getTodos(), new Observer<ApiResponse<List<Todo>>>() {
            @Override
            public void onChanged(ApiResponse<List<Todo>> listApiResponse) {
                if (listApiResponse instanceof ApiResponse.ApiSuccessResponse) {
                    ApiResponse.ApiSuccessResponse<List<Todo>> successResponse = (ApiResponse.ApiSuccessResponse)listApiResponse;
                    mediatorLiveData.addSource(convertToLiveData(successResponse.getBody()), new Observer<List<Todo>>() {
                        @Override
                        public void onChanged(List<Todo> todos) {
                            mediatorLiveData.setValue(Resource.success(todos));
                        }
                    });
                }
            }
        });
        return networkCall;
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
