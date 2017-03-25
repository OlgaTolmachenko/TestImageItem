package com.example.tolmachenko.testitems.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.tolmachenko.testitems.R;
import com.example.tolmachenko.testitems.util.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by Olga Tolmachenko on 24.03.17.
 */

public class DetailsActivity extends AppCompatActivity {

    private ImageView fullPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        fullPhoto = (ImageView) findViewById(R.id.fullPhoto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra(Constants.INTENT_KEY_NAME));
            Picasso
                    .with(this)
                    .load(getIntent().getStringExtra(Constants.INTENT_KEY_PHOTO))
                    .fit()
                    .centerCrop()
                    .into(fullPhoto);
        }
    }
}
