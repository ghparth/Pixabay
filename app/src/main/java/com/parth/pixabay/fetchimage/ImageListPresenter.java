package com.parth.pixabay.fetchimage;

import com.parth.pixabay.fetchimage.repo.PixabayImageRepo;
import com.parth.pixabay.fetchimage.ui.ImageModel;
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
        repo.getImages(query).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(imageModels -> {
                    view.refreshList(imageModels);
                }, throwable -> {
                    view.showErrorMessage(throwable.getMessage());
                });
    }

    public void onListItemClick(ImageModel model) {
        view.openImage(model.getUrl());
    }

}
