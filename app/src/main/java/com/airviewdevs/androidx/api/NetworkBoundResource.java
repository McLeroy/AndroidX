package com.airviewdevs.androidx.api;

import com.airviewdevs.androidx.utils.NetworkResourceLiveData;

import androidx.lifecycle.MediatorLiveData;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private MediatorLiveData<Resource<ResultType>>mediatorLiveData;

    public NetworkBoundResource() {
        mediatorLiveData = new MediatorLiveData<>();
        fetchFromNetwork();
    }

    public void fetchFromNetwork() {

    }

    public NetworkResourceLiveData<Resource<ResultType>>asLiveData();
}
