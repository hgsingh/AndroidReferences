package com.singh.harsukh.fullapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class TweenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween);
    }

    public void animate(View v)
    {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tween_animation);
        v.startAnimation(animation);
        startActivity(GraphicsActivity.aIntent(TweenActivity.this));
    }

    public static Intent aIntent(Context this_Context)
    {
        Intent intent = new Intent(this_Context, TweenActivity.class);
        return intent;
    }
}
