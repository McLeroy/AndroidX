package com.airviewdevs.androidx.models;

import com.airviewdevs.androidx.App;

import androidx.annotation.NonNull;

public class BaseModel {

    @NonNull
    @Override
    public String toString() {
        return App.getGson().toJson(this);
    }

    public static <T extends BaseModel>T fromJson(String json, Class<T>typeOf) {
        return App.getGson().fromJson(json, typeOf);
    }
}
