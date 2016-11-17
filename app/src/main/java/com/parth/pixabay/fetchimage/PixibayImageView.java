package com.parth.pixabay.fetchimage;

/**
 * Created by parth on 11/16/16.
 */
public interface PixibayImageView {

    void refreshList();

    void openImage(String imageUrl);

    void showErrorMessage(String message);

}
