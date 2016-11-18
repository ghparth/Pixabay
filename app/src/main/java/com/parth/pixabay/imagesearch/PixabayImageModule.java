package com.parth.pixabay.imagesearch;

import com.parth.pixabay.imagesearch.data.PixabayImageApi;
import com.parth.pixabay.imagesearch.data.PixabayImageRepo;
import com.parth.pixabay.imagesearch.ui.ImageListPresenter;

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

    @Provides
    @Singleton
    PixabayImageRepo providePixabayImageRepo(PixabayImageApi api) {
        return new PixabayImageRepo(api);
    }

    @Provides
    ImageListPresenter provideImageListPresenter(PixabayImageRepo repo) {
        return new ImageListPresenter(repo);
    }
}
