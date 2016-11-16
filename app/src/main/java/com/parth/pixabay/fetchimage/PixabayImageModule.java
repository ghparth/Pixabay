package com.parth.pixabay.fetchimage;

import com.parth.pixabay.fetchimage.api.PixabayImageApi;
import com.parth.pixabay.fetchimage.repo.PixabayImageRepo;

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
    PixabayImageRepo providePixabayImageRepo(PixabayImageApi api) {
        return new PixabayImageRepo(api);
    }

    @Provides
    ImageListPresenter provideImageListPresenter(PixabayImageRepo repo) {
        return new ImageListPresenter(repo);
    }
}
