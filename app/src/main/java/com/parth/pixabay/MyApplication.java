package com.parth.pixabay;

import android.app.Application;

import com.parth.pixabay.fetchimage.PixabayImageModule;
import com.parth.pixabay.network.NetworkModule;

/**
 * Created by parth on 11/16/16.
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    private MyApplicationComponent mainApplicationComponent;

    public MyApplication() {
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public MyApplicationComponent getMainApplicationComponent() {
        return mainApplicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplicationComponent = prepareApplicationComponent();
    }

    private MyApplicationComponent prepareApplicationComponent() {
        return DaggerMyApplicationComponent.builder().networkModule(new NetworkModule()).
                pixabayImageModule(new PixabayImageModule()).build();
    }
}
