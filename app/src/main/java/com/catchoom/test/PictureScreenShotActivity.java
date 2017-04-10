package com.catchoom.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import blue.arche.sample_1.R;
import blue.arche.sample_1.StaticHolder;

public class PictureScreenShotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_screen_shot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Picture Preview");


        ImageView sourceView=(ImageView)findViewById(R.id.screenshotPicture);




        sourceView.setImageBitmap(StaticHolder.getScreenShotBitmap());

    }

}
