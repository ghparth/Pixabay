package com.parth.pixabay.fetchimage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by parth on 11/16/16.
 */
@Module
public class PixabayImageModule {

    @Provides
    @Singleton
    PixabayImageApi providePixabayImageApi(Retrofit retrofit) {
        return retrofit.create(PixabayImageApi.class);
    }
}
