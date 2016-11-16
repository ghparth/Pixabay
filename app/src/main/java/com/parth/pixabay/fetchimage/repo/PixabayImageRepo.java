package com.parth.pixabay.fetchimage.repo;

import com.parth.pixabay.fetchimage.ui.ImageModel;
import com.parth.pixabay.fetchimage.api.PixabayImageApi;

import java.util.List;

import rx.Observable;

/**
 * Created by parth on 11/16/16.
 */
public class PixabayImageRepo {

    private PixabayImageApi api;

    public PixabayImageRepo(PixabayImageApi api) {
        this.api = api;
    }

    public Observable<List<ImageModel>> getImages(String query) {
        return api.getImages("", query).map(ImageModelMapper::map);
    }
}
