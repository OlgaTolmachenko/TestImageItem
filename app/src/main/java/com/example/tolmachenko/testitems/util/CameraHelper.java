package com.example.tolmachenko.testitems.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Olga Tolmachenko on 24.03.17.
 */

public class CameraHelper {

    private String photoPath;
    private Activity context;

    public CameraHelper(Activity context) {
        this.context = context;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String photoName = "IMG_" + timeStamp + ".jpg";
        String folderName = "TestItemsImages";
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), folderName);
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, photoName);
        photoPath = image.getAbsolutePath();
        return image;
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Log.d(Constants.TAG, ex.toString());
        }
        if (photoFile != null) {
            Uri uriSavedImage = Uri.fromFile(photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            try {
                context.startActivityForResult(takePictureIntent, Constants.REQUEST_CAMERA);
            } catch (ActivityNotFoundException e) {
                Log.d(Constants.TAG, e.toString());
            }
        }
    }

//    public void addPickToGallery() {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File imageFile = new File(photoPath);
//        Uri contentUri = Uri.fromFile(imageFile);
//        mediaScanIntent.setData(contentUri);
//        context.sendBroadcast(mediaScanIntent);
//    }
}
