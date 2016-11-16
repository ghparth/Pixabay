package com.parth.pixabay.fetchimage.api;

import java.io.Serializable;

/**
 * Created by parth on 11/16/16.
 */
public class ServerImageModel implements Serializable {

    private int id;
    private String webformatURL;
    private String previewURL;

    public int getId() {
        return id;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getWebImageURL() {
        return webformatURL;
    }
}
