package com.example.tolmachenko.testitems.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.tolmachenko.testitems.ImageItemsAdapter;
import com.example.tolmachenko.testitems.R;
import com.example.tolmachenko.testitems.model.ImageItem;
import com.example.tolmachenko.testitems.util.CameraHelper;
import com.example.tolmachenko.testitems.util.Constants;
import com.example.tolmachenko.testitems.util.ItemDecorator;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ImageItem> imageItems;
    private ImageItemsAdapter adapter;
    private CameraHelper cameraHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageItems = new ArrayList<>();
        FloatingActionButton addFab = (FloatingActionButton) findViewById(R.id.addFab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] items = {getString(R.string.text_take_from_gallery), getString(R.string.text_take_from_camera)};
                getAlertDialog(items).show();
            }
        });
        setupRecycler();
        cameraHelper = new CameraHelper(this);
    }

    private void setupRecycler() {
        RecyclerView imageRecycler = (RecyclerView) findViewById(R.id.recycler);
        adapter = new ImageItemsAdapter(imageItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        imageRecycler.setAdapter(adapter);
        imageRecycler.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration taskDecor = new ItemDecorator(ContextCompat.getDrawable(this, R.drawable.list_divider));
        imageRecycler.addItemDecoration(taskDecor);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            File imageFile;
            ImageItem image = null;

            switch (requestCode) {
                case Constants.REQUEST_LIBRARY:
                    try {
                        image = getImageFromGallery(data.getData());
                    } catch (NullPointerException | CursorIndexOutOfBoundsException e) {
                        Log.d(Constants.TAG, e.toString());
                        e.printStackTrace();
                    }
                    break;
                case Constants.REQUEST_CAMERA:
//                    cameraHelper.addPickToGallery();
                    imageFile = new File(cameraHelper.getPhotoPath());
                    image = new ImageItem(Uri.fromFile(imageFile), imageFile.getName(), imageFile.lastModified());
                    break;
            }

            imageItems.add(image);
            adapter.notifyDataSetChanged();
        }
    }

    private ImageItem getImageFromGallery(Uri imageUri) throws NullPointerException {
        Cursor cursor = getContentResolver().query(imageUri, null, null, null, null);
        if (cursor == null) {
            throw new NullPointerException();
        }
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(
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

    private AlertDialog getAlertDialog(CharSequence[] items) {
        return new AlertDialog.Builder(this)
                .setTitle(R.string.text_add_photo_title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    requestDocumentsPermissions();
                                } else {
                                    getThumbFromGallery();
                                }
                                break;
                            case 1:
                                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    requestCameraPermissions();
                                } else {
                                    cameraHelper.dispatchTakePictureIntent();
                                }
                                break;
                        }
                    }
                }).create();
    }

    private void getThumbFromGallery() {
        final String chooserTitle = getString(R.string.chooser_title);
        Intent intent = new Intent();
        intent.setType(Constants.IMAGE);
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, chooserTitle), Constants.REQUEST_LIBRARY);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void requestDocumentsPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_DOCUMENTS}, Constants.REQUEST_READ_STORAGE_PERMISSION);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void requestCameraPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_CAMERA_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.REQUEST_READ_STORAGE_PERMISSION: {
                if (grantResults.length > 0
                        && permissions[0].equals(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getThumbFromGallery();
                }
            }

            case Constants.REQUEST_CAMERA_PERMISSION: {
                if (grantResults.length > 0
                        && permissions[0].equals(Manifest.permission.CAMERA)
                        && permissions[1].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    cameraHelper.dispatchTakePictureIntent();
                }
            }
        }
    }
}
