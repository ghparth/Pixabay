package com.parth.pixabay.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.parth.pixabay.MainApplication;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by parth on 11/16/16.
 */
@Module
public class NetworkModule {

    public static final String PIXABAY_BASE_URL = "https://pixabay.com";
    public static final int CACHE_SIZE = 10 * 1024 * 1024;

    @Provides
    @NonNull
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @OkHttpInterceptors
    @Singleton
    @NonNull
    List<Interceptor> provideOkHttpInterceptors() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                message -> Log.d("NetworkRequest", message))
                .setLevel(HttpLoggingInterceptor.Level.BASIC);
        return Collections.singletonList(httpLoggingInterceptor);
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    @NonNull
    List<Interceptor> provideOkHttpNetworkInterceptors() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                message -> Log.d("NetworkCall", message))
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        return Collections.singletonList(httpLoggingInterceptor);
    }


    @Provides
    @Singleton
    Retrofit providePixaBayRetrofit(Gson gson, @OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                    @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
        OkHttpClient client = getPixabayOkHttpClient(interceptors, networkInterceptors);
        return buildRetrofit(client, gson, PIXABAY_BASE_URL).build();
    }

    @NonNull
    private OkHttpClient getPixabayOkHttpClient(@OkHttpInterceptors @NonNull List<Interceptor> interceptors, @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }
        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }
        okHttpBuilder.addInterceptor(new PixabayCacheInterceptor());
        File cacheDir = MainApplication.getInstance().getCacheDir();
        okHttpBuilder.cache(new Cache(cacheDir, CACHE_SIZE));
        return okHttpBuilder.build();
    }

    @NonNull
    private Retrofit.Builder buildRetrofit(OkHttpClient client, Gson gson, String endpoint) {
        return new Retrofit.Builder().baseUrl(endpoint).client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }


}
