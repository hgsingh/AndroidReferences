package com.singh.harsukh.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    /*The Android Service class is for background tasks that may be active but not visible
    on the screen. A music-playing application would likely be implemented as a service
    to continue to play music while a user might be viewing web pages. Services also allow
    applications to share functions through long-term connections. This practice is reminiscent
    of Internet services such as FTP and HTTP, which wait until a request from a
    client triggers them. The Android platform avoids reclaiming service resources, so once
    a service starts, it is likely to be available unless memory gets extremely constrained.
    Like Activity , the Service class offers methods that control its life cycle, such as stop-
    ping and restarting the service.*/

    /*
        three type of services
        -simple service
        -intent service
        -binded service

        Life cycle of service include
        --startService
        -- bindService
        --OnCreate --called first
        --OnStartCommand --starts the service
        --onBind --binds service
        --onUnbind
        --onDestroy
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nextActivity(View v)
    {
        if(v.getId() == R.id.button3)
            startActivity(SecondActivity.aIntent(getApplicationContext()));
    }

    //service is started by passing an intent
    public void startService(View v)
    {
        if(v.getId() == R.id.button)
        {
            Intent intent = SimpleService.aIntent(getApplicationContext());
            intent.putExtra("message", "This is from MainActivity");
            startService(intent);
        }
    }

    //service is stopped by calling the stop service command
    public void stopService(View v)
    {
        if(v.getId() == R.id.button2)
        {
            stopService(SimpleService.aIntent(getApplicationContext()));
        }
    }
}
