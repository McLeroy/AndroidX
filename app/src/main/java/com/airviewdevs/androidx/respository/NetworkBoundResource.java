package com.airviewdevs.androidx.respository;

import com.airviewdevs.androidx.AppExecutors;
import com.airviewdevs.androidx.api.ApiResponse;
import com.airviewdevs.androidx.api.Resource;
import com.airviewdevs.androidx.utils.DebugUtils;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final AppExecutors appExecutors;
    private final MediatorLiveData<Resource<ResultType>> result;

    public NetworkBoundResource(AppExecutors appExecutors) {
        DebugUtils.debug(NetworkBoundResource.class, "Creating new");
        this.appExecutors = appExecutors;
        result = new MediatorLiveData<>();
//        setValue(Resource.loading(null));
        fetchFromNetwork();
    }


    public void fetchFromNetwork() {
        DebugUtils.debug(NetworkBoundResource.class, "Fetching from network");
        LiveData<ResultType>dbSource = loadFromDb();
        LiveData<ApiResponse<RequestType>>apiResponse = createCall();
        result.addSource(dbSource, resultType ->
                setValue(Resource.loading(resultType)));
        result.addSource(apiResponse, response -> {
            DebugUtils.debug(NetworkBoundResource.class, "Response: "+response.getClass().getSimpleName());
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            if (response instanceof ApiResponse.ApiSuccessResponse) {
                appExecutors.getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
//                        saveCallResult(processResponse((ApiResponse.ApiSuccessResponse<RequestType>) response));
                        appExecutors.getMainThread().execute(() ->
                                result.addSource(convertToLiveData(processResponse((ApiResponse.ApiSuccessResponse<RequestType>) response)), resultType ->
                                        setValue(Resource.success(resultType))));
                    }
                });
            }else if (response instanceof ApiResponse.ApiEmptyResponse) {
                appExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        result.addSource(convertToLiveData(null), new Observer<ResultType>() {
                            @Override
                            public void onChanged(ResultType resultType) {
                                setValue(Resource.success(resultType));
                            }
                        });
                    }
                });
            }else if (response instanceof ApiResponse.ApiErrorResponse) {
                appExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        onFetchFailed();
                        result.addSource(convertToLiveData(null), new Observer<ResultType>() {
                            @Override
                            public void onChanged(ResultType resultType) {
                                setValue(Resource.error(((ApiResponse.ApiErrorResponse<RequestType>) response).getEx(),
                                        resultType));
                            }
                        });
                    }
                });
            }
        });
    }

    private void setValue(Resource<ResultType>newValue) {
        result.setValue(newValue);
    }

    protected void onFetchFailed(){}

    public LiveData<Resource<ResultType>>asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse.ApiSuccessResponse<RequestType>response) {
        return response.getBody();
    }

    protected abstract LiveData<ResultType>loadFromDb();

    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    protected abstract void saveCallResult(RequestType requestType);

    protected abstract LiveData<ResultType>convertToLiveData(RequestType requestType);


}
