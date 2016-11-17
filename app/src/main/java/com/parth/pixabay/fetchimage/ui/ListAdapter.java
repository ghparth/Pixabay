package com.parth.pixabay.fetchimage.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parth.pixabay.MainApplication;
import com.parth.pixabay.R;
import com.parth.pixabay.utils.ChildClickListener;
import com.parth.pixabay.utils.MyImageLoader;

import java.util.List;

/**
 * Created by parth on 11/16/16.
 */

public class ListAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private ListDS dataProvider;
    private ChildClickListener<ImageModel> listener;

    public ListAdapter(ListDS dataProvider, ChildClickListener listener) {
        this.dataProvider = dataProvider;
        this.listener = listener;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageModel model = dataProvider.getData().get(position);
        MyImageLoader imageLoader = MainApplication.getInstance().getMainApplicationComponent().getImageLoader();
        imageLoader.loadImage(holder.imageView, model.getPreviewUrl());
        holder.itemView.setOnClickListener(view -> {
            listener.onChildViewClicked(ChildClickListener.IMAGE_CLICKED, view, model);
        });
    }

    @Override
    public int getItemCount() {
        int size = dataProvider.getData().size();
        return size;
    }


    public interface ListDS {
        List<ImageModel> getData();
    }
}
