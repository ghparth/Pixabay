package com.parth.pixabay;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parth.pixabay.fetchimage.ImageListPresenter;
import com.parth.pixabay.fetchimage.ui.ImageModel;
import com.parth.pixabay.fetchimage.ui.ListAdapter;
import com.parth.pixabay.fetchimage.ui.PixibayImageView;
import com.parth.pixabay.utils.ChildClickListener;
import com.parth.pixabay.utils.MyImageLoader;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements PixibayImageView, ChildClickListener<ImageModel> {

    @Inject
    ImageListPresenter presenter;

    @BindView(R.id.search_txt)
    EditText searchText;

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View baseView = findViewById(R.id.activity_main);
        MainApplication.getInstance().getMainApplicationComponent().inject(this);
        ButterKnife.bind(this, baseView);
        presenter.initView(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @OnClick(R.id.submit)
    void onSubmitClicked() {
        presenter.onSearchTriggered(searchText.getText().toString());
    }

    @Override
    public void refreshList(List<ImageModel> data) {
        Log.d("RefreshList", data.toString());
        recyclerView.setAdapter(new ListAdapter(data, this));
    }

    @Override
    public void openImage(String imageUrl) {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.fs_dialog);
        ImageView image = (ImageView) dialog.findViewById(R.id.image_view);
        MyImageLoader imageLoader = MainApplication.getInstance().getMainApplicationComponent()
                .getImageLoader();
        imageLoader.loadImage(image, imageUrl);
        dialog.show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChildViewClicked(int id, View v, ImageModel model) {
        switch (id) {
            case ChildClickListener.IMAGE_CLICKED:
                presenter.onListItemClick(model);
                break;
        }
    }
}
