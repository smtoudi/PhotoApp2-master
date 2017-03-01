package com.example.loop.photoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = (ImageView) findViewById(R.id.image);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Picasso.with(this).load(getIntent().getData()).fit().into(imageView);
    }
}
