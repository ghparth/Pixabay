package com.parth.pixabay.imagesearch.data;

import android.util.Log;

import com.parth.pixabay.imagesearch.ui.ImageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parth on 11/16/16.
 */
public class ImageModelMapper {

    public static ImageRepoResponse map(PixabayServerResponse pixabayServerResponse) {
        List<ImageModel> data = new ArrayList<>();
        for (ServerImageModel serverImageModel : pixabayServerResponse.getHits()) {
            ImageModel mappedModel = new ImageModel(serverImageModel.getId(), serverImageModel.getPreviewURL(), serverImageModel.getWebImageURL());
            data.add(mappedModel);
        }
        Log.d("Mapper", "Server response : " + pixabayServerResponse + "\nMapped to local :" + data);
        return new ImageRepoResponse(pixabayServerResponse.getTotalHits(), data);
    }
}
