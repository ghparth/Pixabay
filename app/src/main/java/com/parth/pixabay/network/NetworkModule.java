package com.parth.pixabay.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @NonNull
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    @NonNull
    @Singleton
    public OkHttpClient provideOkHttpClient(
            @OkHttpInterceptors @NonNull List<Interceptor> interceptors,
            @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        return okHttpBuilder.build();
    }

    @Provides
    @OkHttpInterceptors
    @Singleton
    @NonNull
    public List<Interceptor> provideOkHttpInterceptors() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                message -> Log.d("Network", message))
                .setLevel(HttpLoggingInterceptor.Level.BASIC);
        return Collections.singletonList(httpLoggingInterceptor);
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    @NonNull
    public List<Interceptor> provideOkHttpNetworkInterceptors() {
        return Collections.emptyList();
    }


    @Provides
    @Singleton
    Retrofit providePixaBayRetrofit(OkHttpClient client, Gson gson) {
        return buildRetrofit(client, gson, "https://pixabay.com").build();
    }

    @NonNull
    private Retrofit.Builder buildRetrofit(OkHttpClient client, Gson gson, String endpoint) {
        return new Retrofit.Builder().baseUrl(endpoint).client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()); // Fail early
    }


}
