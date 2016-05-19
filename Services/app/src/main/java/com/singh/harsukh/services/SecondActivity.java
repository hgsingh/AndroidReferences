package com.singh.harsukh.services;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public static Intent aIntent(Context context)
    {
        return new Intent(context, SecondActivity.class);
    }

    public void startService(View v)
    {
        if(v.getId() == R.id.button4)
        {
            Intent intent = PollService.aIntent(getApplicationContext());
            startService(intent);
        }
    }
    public void nextActivity(View v)
    {
        if(v.getId() == R.id.button5)
            startActivity(ThirdActivity.aIntent(getApplicationContext()));
    }
}
