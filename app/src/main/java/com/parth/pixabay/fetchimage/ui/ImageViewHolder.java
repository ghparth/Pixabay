package com.parth.pixabay.fetchimage.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.parth.pixabay.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parth on 11/16/16.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_view)
    ImageView imageView;

    public ImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
