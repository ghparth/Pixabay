package com.parth.pixabay.fetchimage;


import com.parth.pixabay.fetchimage.repo.PixabayImageRepo;
import com.parth.pixabay.fetchimage.ui.ImageModel;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.internal.util.SubscriptionList;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by parth on 11/16/16.
 */
public class ImageListPresenter {

    private PixabayImageRepo repo;
    private PixibayImageView view;

    private BehaviorSubject<String> querySubject = BehaviorSubject.create("");
    private BehaviorSubject<Integer> paginationSubject = BehaviorSubject.create(1);

    private List<ImageModel> data = new ArrayList<>();
    private SubscriptionList subscriptionList;

    public ImageListPresenter(PixabayImageRepo repo) {
        this.repo = repo;
    }

    public void initView(PixibayImageView view) {
        this.view = view;
        subscriptionList = new SubscriptionList();
        subscriptionList.add(querySubject.asObservable().filter(this::isQueryValid)
                .subscribe(s -> {
                    data.clear();
                    paginationSubject.onNext(1);
                }));

        subscriptionList.add(paginationSubject.asObservable()
                .filter(pageNo -> isQueryValid(querySubject.getValue()))
                .subscribe(pageNo -> {
                    repo.getImages(querySubject.getValue(), paginationSubject.getValue()).
                            subscribeOn(Schedulers.io()).
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribe(imageModels -> {
                                data.addAll(imageModels);
                                view.refreshList();
                                if (imageModels.isEmpty()) {
                                    view.showErrorMessage("No more images");
                                }
                            }, throwable -> {
                                view.showErrorMessage(throwable.getMessage());
                            });
                }));

    }

    private boolean isQueryValid(String query) {
        return query != null && !query.isEmpty();
    }

    public void onSearchTriggered(String query) {
        if (query == null || query.isEmpty()) {
            view.showErrorMessage("Invalid query");
            return;
        }
        if (!query.equals(querySubject.getValue())) {
            querySubject.onNext(query);
        }
    }

    public List<ImageModel> getData() {
        return data;
    }

    public void onRequestNextPage() {
        paginationSubject.onNext(paginationSubject.getValue() + 1);
    }

    public void onListItemClick(ImageModel model) {
        view.openImage(model.getUrl());
    }

    public void onDestroy() {
        if (subscriptionList != null) {
            subscriptionList.unsubscribe();
        }
    }
}
