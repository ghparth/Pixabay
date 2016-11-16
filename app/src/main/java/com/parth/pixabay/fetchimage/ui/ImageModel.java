package com.parth.pixabay.fetchimage.ui;

import java.io.Serializable;

/**
 * Created by parth on 11/16/16.
 */
public class ImageModel implements Serializable {

    private int id;
    private String previewUrl;
    private String url;

    public ImageModel(int id, String previewUrl, String url) {
        this.id = id;
        this.previewUrl = previewUrl;
        this.url = url;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public String getUrl() {
        return url;
    }
}
