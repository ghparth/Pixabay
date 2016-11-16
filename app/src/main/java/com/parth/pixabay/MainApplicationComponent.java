package com.parth.pixabay;


import com.parth.pixabay.fetchimage.PixabayImageModule;
import com.parth.pixabay.network.NetworkModule;
import com.parth.pixabay.utils.CoreModule;
import com.parth.pixabay.utils.MyImageLoader;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by parthg on 16/09/16.
 */
@Singleton
@Component(modules = {NetworkModule.class, CoreModule.class, PixabayImageModule.class})
public interface MainApplicationComponent {

    MyImageLoader getImageLoader();

    void inject(MainActivity mainActivity);
}
