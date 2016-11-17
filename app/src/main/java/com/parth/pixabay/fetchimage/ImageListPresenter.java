package com.parth.pixabay.fetchimage;


import android.util.Log;

import com.parth.pixabay.fetchimage.repo.ImageModelMapper;
import com.parth.pixabay.fetchimage.repo.PixabayImageRepo;
import com.parth.pixabay.fetchimage.ui.ImageModel;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.internal.util.SubscriptionList;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by parth on 11/16/16.
 */
public class ImageListPresenter {

    public static final int PAGE_SIZE = 20;
    private PixabayImageRepo repo;
    private PixibayImageView view;

    private PublishSubject<String> querySubject = PublishSubject.create();
    private PublishSubject<Integer> paginationSubject = PublishSubject.create();

    private List<ImageModel> data = new ArrayList<>();
    private String query;
    private int pageNo;
    private SubscriptionList subscriptionList = new SubscriptionList();;

    public ImageListPresenter(PixabayImageRepo repo) {
        this.repo = repo;
    }

    public void initView(PixibayImageView view) {
        this.view = view;
        subscriptionList.unsubscribe();
        subscriptionList.clear();
        subscriptionList.add(querySubject.asObservable().filter(this::isQueryValid)
                .filter(search -> !search.equals(query))
                .subscribe(search -> {
                    query = search;
                    pageNo = 0;
                    data.clear();
                    view.refreshList();
                    paginationSubject.onNext(pageNo + 1);
                }));

        subscriptionList.add(paginationSubject.asObservable()
                .filter(pageNo -> isQueryValid(query))
                .filter(page -> page > pageNo)
                .subscribe(page -> {
                    pageNo = page;
                    repo.getImages(query, pageNo, PAGE_SIZE).
                            subscribeOn(Schedulers.io()).
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribe(serverResponse -> {
                                view.setPaginationSupport(serverResponse.getTotalHits() > page * PAGE_SIZE);
                                List<ImageModel> imageModels = ImageModelMapper.map(serverResponse);
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

    public void onSearchTriggered(String search) {
        if (search == null || search.isEmpty()) {
            view.showErrorMessage("Invalid search");
            return;
        }
        if (!search.equals(query)) {
            querySubject.onNext(search);
        }
    }

    public List<ImageModel> getData() {
        return data;
    }

    public void onRequestNextPage() {
        int nextPage = pageNo + 1;
        Log.d("Presenter", "Next page requested query: " + query + " pageNo: " + nextPage);
        paginationSubject.onNext(nextPage);
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
