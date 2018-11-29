package com.airviewdevs.androidx.respositories;

import com.airviewdevs.androidx.AppExecutors;

import androidx.lifecycle.MutableLiveData;

public abstract class BaseRepository {
    private final AppExecutors appExecutors;

    protected BaseRepository() {
        this.appExecutors = AppExecutors.get();
    }

    public AppExecutors getAppExecutors() {
        return appExecutors;
    }

    public static <T>MutableLiveData<T> convertResultToLiveData(T item) {
        MutableLiveData<T>liveData = new MutableLiveData<>();
        liveData.setValue(item);
        return liveData;
    }
}
