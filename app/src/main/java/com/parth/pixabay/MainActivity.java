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

import com.parth.pixabay.imagesearch.ui.ImageListPresenter;
import com.parth.pixabay.imagesearch.ui.ImageModel;
import com.parth.pixabay.imagesearch.ui.ListAdapter;
import com.parth.pixabay.utils.ChildClickListener;
import com.parth.pixabay.utils.MyImageLoader;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements ImageListPresenter.PixibayImageView, ChildClickListener<ImageModel>, ListAdapter.ListDataProvider {

    @Inject
    ImageListPresenter presenter;

    @BindView(R.id.search_txt)
    EditText searchText;

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.next_page)
    View nextPage;

    private ListAdapter adapter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View baseView = findViewById(R.id.activity_main);
        MainApplication.getInstance().getMainApplicationComponent().inject(this);
        unbinder = ButterKnife.bind(this, baseView);
        presenter.initView(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListAdapter(this, this);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.submit)
    void onSubmitClicked() {
        Log.d("Activity", "Search clicked");
        presenter.onSearchTriggered(searchText.getText().toString());
    }

    @OnClick(R.id.next_page)
    void onNextClicked() {
        presenter.onRequestNextPage();
    }

    @Override
    public void refreshList() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
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
    public void setPaginationSupport(boolean visibility) {
        Log.d("Activity", "Pagination : " + visibility);
        nextPage.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onChildViewClicked(int id, View v, ImageModel model) {
        switch (id) {
            case ChildClickListener.IMAGE_CLICKED:
                presenter.onListItemClick(model);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public List<ImageModel> getData() {
        return presenter.getData();
    }
}
