package com.airviewdevs.androidx.utils;

import androidx.lifecycle.LiveData;

public abstract class NetworkResourceLiveData<T> extends LiveData<T> {

    @Override
    protected void onActive() {
        super.onActive();
        loadData();
    }

    protected  void loadData() {

    }
}
