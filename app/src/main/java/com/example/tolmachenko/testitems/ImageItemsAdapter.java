package com.example.tolmachenko.testitems;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Olga Tolmachenko on 24.03.17.
 */

public class ImageItemsAdapter extends RecyclerView.Adapter<ImageItemsAdapter.ItemsViewHolder> {

    private ArrayList<ImageItem> imageItems;

    public ImageItemsAdapter(ArrayList<ImageItem> imageItems) {
        this.imageItems = imageItems;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        holder.thumb.setImageURI(Uri.parse(imageItems.get(position).thumbUri));
        holder.imageData.setText(imageItems.get(position).imageData);
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (imageItems != null) {
            size = imageItems.size();
        }
        return size;
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumb;
        public TextView imageData;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            imageData = (TextView) itemView.findViewById(R.id.imageData);
        }
    }
}
