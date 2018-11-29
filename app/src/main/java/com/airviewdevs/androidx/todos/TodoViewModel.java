package com.airviewdevs.androidx.todos;

import com.airviewdevs.androidx.ApiResponse;
import com.airviewdevs.androidx.App;
import com.airviewdevs.androidx.api.Resource;
import com.airviewdevs.androidx.models.Todo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoViewModel extends ViewModel {
    private MutableLiveData<Resource<List<Todo>>>liveData;
    private MediatorLiveData<Resource<List<Todo>>>mediatorLiveData;


    public TodoViewModel() {
        liveData = new MutableLiveData<>();
        mediatorLiveData = new MediatorLiveData<>();
    }

    public LiveData<Resource<List<Todo>>>getTodos(){
        return liveData;
    }

    public void loadFromNetwork() {
        liveData.setValue(Resource.loading(null));
        mediatorLiveData.addSource(App.getApiService().getTodos(), new Observer<ApiResponse<List<Todo>>>() {
            @Override
            public void onChanged(ApiResponse<List<Todo>> listApiResponse) {
                if (listApiResponse instanceof ApiResponse.ApiSuccessResponse) {
                    ApiResponse.ApiSuccessResponse successResponse = (ApiResponse.ApiSuccessResponse)listApiResponse
                    liveData.setValue();
                }
            }
        });
        App.getApiService().getTodosList().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.body() != null)
                    liveData.setValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                liveData.setValue(Resource.error(t.getMessage(), 500));
            }
        });
    }

}
