package com.parth.pixabay.utils;

import com.parth.pixabay.MainApplication;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by parth on 11/16/16.
 */
@Module
public class CoreModule {

    @Provides
    @Singleton
    public Picasso getPicasoInstance() {
        OkHttpClient picassoClient = new OkHttpClient();
        return new Picasso.Builder(MainApplication.getInstance()).build();
    }

    @Provides
    @Singleton
    public MyImageLoader getImageLoader(Picasso picasso) {
        return new MyImageLoader(picasso);
    }
}
