package com.airviewdevs.androidx;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static AppExecutors instance = new AppExecutors();
    private Executor diskIO;
    private Executor networkIO;
    private Executor mainThread;
    public static AppExecutors get() {
        return instance;
    }

    private AppExecutors() {
        Executors.newSingleThreadExecutor();
        diskIO = Executors.newSingleThreadExecutor();
        networkIO = Executors.newFixedThreadPool(3);
        mainThread = new MainThreadExecutor();
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private final class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
