package com.parth.pixabay.fetchimage.repo;

import com.parth.pixabay.fetchimage.api.PixabayImageApi;
import com.parth.pixabay.fetchimage.ui.ImageModel;

import java.util.List;

import rx.Observable;

/**
 * Created by parth on 11/16/16.
 */
public class PixabayImageRepo {

    //    private static final String PIXABAY_API_KEY = "3777143-215b646e4ad11d4f2e8d5fa6a";
    private static final String PIXABAY_API_KEY = "3784386-81068bc38e20e1f88013281d2";
    private PixabayImageApi api;

    public PixabayImageRepo(PixabayImageApi api) {
        this.api = api;
    }

    public Observable<List<ImageModel>> getImages(String query, int pageNo) {
        return api.getImages(PIXABAY_API_KEY, query, pageNo).map(ImageModelMapper::map);
    }
}
