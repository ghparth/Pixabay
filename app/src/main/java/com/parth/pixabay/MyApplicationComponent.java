package com.parth.pixabay;


import com.parth.pixabay.fetchimage.PixabayImageModule;
import com.parth.pixabay.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by parthg on 16/09/16.
 */


@Singleton
@Component(modules = {NetworkModule.class, PixabayImageModule.class})
public interface MyApplicationComponent {

}
