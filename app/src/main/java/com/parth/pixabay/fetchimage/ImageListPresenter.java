package com.parth.pixabay.fetchimage;

import com.parth.pixabay.fetchimage.repo.PixabayImageRepo;
import com.parth.pixabay.fetchimage.ui.PixibayImageView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by parth on 11/16/16.
 */

public class ImageListPresenter {

    private PixabayImageRepo repo;
    private PixibayImageView view;

    public ImageListPresenter(PixabayImageRepo repo) {
        this.repo = repo;
    }

    public void initView(PixibayImageView view) {
        this.view = view;
    }

    public void onSearchTriggered(String query) {
        repo.getImages(query).observeOn(Schedulers.io()).
                subscribeOn(AndroidSchedulers.mainThread()).
                subscribe(imageModels -> {
                    view.refreshList(imageModels);
                });
    }

}
