package com.parth.pixabay.imagesearch.data;

import com.parth.pixabay.imagesearch.ui.ImageModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by parth on 11/18/16.
 */

public class ImageRepoResponse implements Serializable {

    private int totalResult;
    private List<ImageModel> result;

    public ImageRepoResponse(int totalResult, List<ImageModel> result) {
        this.totalResult = totalResult;
        this.result = result;
    }

    public int getTotalResultCount() {
        return totalResult;
    }

    public List<ImageModel> getPageResult() {
        return result;
    }
}
