package com.airviewdevs.androidx.api;

import com.airviewdevs.androidx.ApiResponse;
import com.airviewdevs.androidx.AppExecutors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public abstract class NetworkBasedResponse<RequestType, ResultType> {

    private MediatorLiveData<Resource<ResultType>> mediatorLiveData = new MediatorLiveData<>();
    private final AppExecutors appExecutors;

    public NetworkBasedResponse(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        fetchFromNetwork();
    }

    public void fetchFromNetwork() {
        mediatorLiveData.setValue(Resource.loading(null));
        LiveData<ApiResponse<RequestType>> networkCall = createCall();
        mediatorLiveData.addSource(networkCall, requestTypeApiResponse -> {
            mediatorLiveData.removeSource(networkCall);
            if (requestTypeApiResponse instanceof ApiResponse.ApiSuccessResponse) {
                ApiResponse.ApiSuccessResponse<RequestType> successResponse = (ApiResponse.ApiSuccessResponse<RequestType>) requestTypeApiResponse;
                appExecutors.getNetworkIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        //I have simulated slow network connection here
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        appExecutors.getMainThread().execute(() -> {
                            mediatorLiveData.addSource(convertToLiveData(successResponse.getBody()), new Observer<ResultType>() {
                                @Override
                                public void onChanged(ResultType resultType) {
                                    mediatorLiveData.setValue(Resource.success(resultType));
                                }
                            });
                        });
                    }
                });
            } else if (requestTypeApiResponse instanceof ApiResponse.ApiErrorResponse) {
                ApiResponse.ApiErrorResponse apiErrorResponse = (ApiResponse.ApiErrorResponse) requestTypeApiResponse;
                mediatorLiveData.setValue(Resource.error(apiErrorResponse.getEx()));
            }
        });
    }


//    public abstract LiveData<ResultType>getFromDb();

    public abstract LiveData<ApiResponse<RequestType>>createCall();

    protected RequestType processResponse(ApiResponse.ApiSuccessResponse<RequestType>response) {
        return response.getBody();
    }

    public abstract LiveData<ResultType>convertToLiveData(RequestType requestType);

//    protected abstract void saveCallResult(RequestType requestType);

    public LiveData<Resource<ResultType>>asLiveData() {
        return mediatorLiveData;
    }
}
