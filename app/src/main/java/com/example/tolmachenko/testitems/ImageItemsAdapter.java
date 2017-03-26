package com.example.tolmachenko.testitems;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tolmachenko.testitems.activity.DetailsActivity;
import com.example.tolmachenko.testitems.model.ImageItem;
import com.example.tolmachenko.testitems.util.Constants;
import com.squareup.picasso.Picasso;

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
        Context context = holder.itemView.getContext();
        int photoSideDimen = context.getResources().getDimensionPixelOffset(R.dimen.picasso_100dp);
        Picasso.with(context).load(imageItems.get(position).getImageThumb()).resize(photoSideDimen, photoSideDimen).centerCrop().into(holder.imageThumb);
        holder.imageName.setText(imageItems.get(position).getImageName());
        holder.imageTime.setText(String.valueOf(imageItems.get(position).getImageTime()));
        holder.setSelectedItem(imageItems.get(position));
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (imageItems != null) {
            size = imageItems.size();
        }
        return size;
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageThumb;
        public TextView imageName;
        public TextView imageTime;

        private ImageItem selectedItem;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            imageThumb = (ImageView) itemView.findViewById(R.id.thumb);
            imageName = (TextView) itemView.findViewById(R.id.name);
            imageTime = (TextView) itemView.findViewById(R.id.time);
            itemView.setOnClickListener(this);
        }

        public ImageItem getSelectedItem() {
            return selectedItem;
        }

        public void setSelectedItem(ImageItem selectedItem) {
            this.selectedItem = selectedItem;
        }

        @Override
        public void onClick(View view) {
            Intent openFullPhoto = new Intent(view.getContext(), DetailsActivity.class);
            openFullPhoto.putExtra(Constants.INTENT_KEY_NAME, selectedItem.getImageName());
            openFullPhoto.putExtra(Constants.INTENT_KEY_PHOTO, selectedItem.getImageThumb().toString());
            view.getContext().startActivity(openFullPhoto);
        }
    }
}
