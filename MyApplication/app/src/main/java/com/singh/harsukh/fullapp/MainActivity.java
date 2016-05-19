package com.singh.harsukh.fullapp;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView im = (ImageView) findViewById(R.id.imageView);
        im.setBackgroundResource(R.drawable.animation);
        im.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AnimationDrawable an = (AnimationDrawable) im.getBackground();
                an.start();
                startActivity(TweenActivity.aIntent(MainActivity.this));
            }

        });
    }
}
