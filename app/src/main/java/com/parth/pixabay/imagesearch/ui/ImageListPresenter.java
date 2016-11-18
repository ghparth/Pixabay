package com.parth.pixabay.imagesearch.ui;


import android.util.Log;

import com.parth.pixabay.BuildConfig;
import com.parth.pixabay.imagesearch.data.PixabayImageRepo;

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
    private String currentQuery;
    private int currentPage;
    private SubscriptionList subscriptionList;

    public ImageListPresenter(PixabayImageRepo repo) {
        this.repo = repo;
    }

    public void initView(PixibayImageView view) {
        this.view = view;
        subscriptionList = new SubscriptionList();
        subscriptionList.add(querySubject.asObservable()
                .filter(query -> isViewAttached())
                .filter(this::isQueryValid)
                .filter(search -> !search.equals(currentQuery))
                .subscribe(search -> {
                    startNewSearch(search);
                    view.refreshList();
                    onRequestNextPage();
                }));

        subscriptionList.add(paginationSubject.asObservable()
                .filter(pageNo -> isViewAttached())
                .filter(pageNo -> isQueryValid(currentQuery))
                .filter(pageNo -> pageNo > currentPage)
                .subscribe(pageNo -> {
                    view.setPaginationSupport(false);
                    fetchImagesFromServer(pageNo);
                }));

    }

    private void startNewSearch(String search) {
        currentQuery = search;
        currentPage = 0;
        data.clear();
    }

    private void fetchImagesFromServer(int pageNo) {
        repo.getImages(currentQuery, pageNo, PAGE_SIZE).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(repoResponse -> {
                    List<ImageModel> imageModels = repoResponse.getPageResult();
                    if (imageModels.isEmpty()) {
                        view.showErrorMessage("No images returned from server");
                    } else {
                        currentPage = pageNo;
                        data.addAll(imageModels);
                        view.setPaginationSupport(repoResponse.getTotalResultCount() > data.size());
                        view.refreshList();
                    }
                }, throwable -> {
                    view.showErrorMessage(throwable.getMessage());
                });
    }

    private boolean isViewAttached() {
        return view != null;
    }

    private boolean isQueryValid(String query) {
        boolean validQuery = query != null && !query.isEmpty();
        if (BuildConfig.DEBUG && !validQuery) {
            view.showErrorMessage("Not a valid currentQuery");
        }
        return validQuery;
    }

    public void onSearchTriggered(String search) {
        if (search == null || search.isEmpty()) {
            view.showErrorMessage("Invalid search");
            return;
        }
        if (!search.equals(currentQuery)) {
            Log.d("Presenter", "Query getting processed :" + search);
            querySubject.onNext(search);
        } else {
            view.showErrorMessage("Please enter a new search term");
        }
    }

    public List<ImageModel> getData() {
        return data;
    }

    public void onRequestNextPage() {
        int nextPage = currentPage + 1;
        Log.d("Presenter", "Next page requested currentQuery: " + currentQuery + " currentPage: " + nextPage);
        paginationSubject.onNext(nextPage);
    }

    public void onListItemClick(ImageModel model) {
        view.openImage(model.getUrl());
    }

    public void onDestroy() {
        if (subscriptionList != null) {
            subscriptionList.unsubscribe();
        }
        view = null;
        currentPage = 0;
        currentQuery = null;
    }


    public interface PixibayImageView {

        void refreshList();

        void openImage(String imageUrl);

        void showErrorMessage(String message);

        void setPaginationSupport(boolean enabled);
    }
}
