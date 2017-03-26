package com.example.tolmachenko.testitems.util;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.tolmachenko.testitems.R;
import com.example.tolmachenko.testitems.model.ImageItem;

/**
 * Created by Olga Tolmachenko on 26.03.17.
 */

public class GalleryHelper {

    private Activity context;

    public GalleryHelper(Activity context) {
        this.context = context;
    }

    public void dispatchGalleryIntent() {
        final String chooserTitle = context.getString(R.string.chooser_title);
        Intent intent = new Intent();
        intent.setType(Constants.IMAGE);
        intent.setAction(Intent.ACTION_PICK);
        context.startActivityForResult(Intent.createChooser(intent, chooserTitle), Constants.REQUEST_LIBRARY);
    }

    public ImageItem getImageFromGallery(Uri imageUri) throws NullPointerException {
        Cursor cursor = context.getContentResolver().query(imageUri, null, null, null, null);
        if (cursor == null) {
            throw new NullPointerException();
        }
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        if (cursor == null) {
            throw new NullPointerException();
        }
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
        long time = Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)));
        cursor.close();

        return new ImageItem(imageUri, name, time);
    }
}
