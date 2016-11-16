package com.parth.pixabay.utils;

import android.view.View;

/**
 * Created by parth on 11/16/16.
 */

public interface ChildClickListener<T> {

    int IMAGE_CLICKED = 100;

    void onChildViewClicked(int id,View v, T model);
}
