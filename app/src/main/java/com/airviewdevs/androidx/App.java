package com.airviewdevs.androidx;

import android.app.Application;

import com.airviewdevs.androidx.api.ApiService;
import com.airviewdevs.androidx.utils.DebugUtils;
import com.airviewdevs.androidx.utils.LiveDataCallAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private static Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        gson = new GsonBuilder().create();
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request();
                        DebugUtils.debug(App.class, "Making network request: "+request.url());
                        return chain.proceed(request);
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();
    }

    public static ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

    public static Gson getGson() {
        return gson;
    }
}
