package com.parth.pixabay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parth.pixabay.fetchimage.ImageListPresenter;
import com.parth.pixabay.fetchimage.ui.ImageModel;
import com.parth.pixabay.fetchimage.ui.PixibayImageView;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements PixibayImageView {

    @Inject
    private ImageListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void refreshList(List<ImageModel> data) {

    }
}
