package com.parth.pixabay;

import android.app.Application;

import com.parth.pixabay.fetchimage.PixabayImageModule;
import com.parth.pixabay.network.NetworkModule;

/**
 * Created by parth on 11/16/16.
 */
public class MainApplication extends Application {

    private static MainApplication instance;
    private MainApplicationComponent mainApplicationComponent;

    public MainApplication() {
        instance = this;
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public MainApplicationComponent getMainApplicationComponent() {
        return mainApplicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplicationComponent = prepareApplicationComponent();
    }

    private MainApplicationComponent prepareApplicationComponent() {
        return DaggerMainApplicationComponent.builder().networkModule(new NetworkModule()).
                pixabayImageModule(new PixabayImageModule()).build();
    }
}
