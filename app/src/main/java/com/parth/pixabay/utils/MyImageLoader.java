package com.parth.pixabay.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by parth on 11/16/16.
 */
public class MyImageLoader {

    private Picasso picasso;

    public MyImageLoader(Picasso picasso) {
        this.picasso = picasso;
    }

    public void loadImage(ImageView view, String url) {
        RequestCreator request = picasso.load(url);
        request.into(view);
    }
}
