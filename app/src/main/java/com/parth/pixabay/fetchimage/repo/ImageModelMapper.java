package com.parth.pixabay.fetchimage.repo;

import com.parth.pixabay.fetchimage.api.PixabayServerResponse;
import com.parth.pixabay.fetchimage.api.ServerImageModel;
import com.parth.pixabay.fetchimage.ui.ImageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parth on 11/16/16.
 */
public class ImageModelMapper {

    public static List<ImageModel> map(PixabayServerResponse pixabayServerResponse) {
        List<ImageModel> data = new ArrayList<>();
        for (ServerImageModel serverImageModel :
                pixabayServerResponse.getHits()) {
            ImageModel mappedModel = new ImageModel(serverImageModel.getId(), serverImageModel.getPreviewURL(), serverImageModel.getWebImageURL());
            data.add(mappedModel);
        }
        return data;
    }
}
