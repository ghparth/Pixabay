package com.parth.pixabay.fetchimage.api;

import java.io.Serializable;
import java.util.List;

/**
 * Created by parth on 11/16/16.
 */
public class PixabayServerResponse implements Serializable{

    private int totalHits;
    private List<ServerImageModel> hits;

    public List<ServerImageModel> getHits() {
        return hits;
    }

    public int getTotalHits() {
        return totalHits;
    }
}
