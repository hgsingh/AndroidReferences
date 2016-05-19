package com.singh.harsukh.broadcastreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/*
 Broadcast Receivers receive messages from other applications or the
 operating system. These messages are sometime called events or intents.
 For example, applications can also initiate broadcasts to let other applications know
 that some data has been downloaded to the device and is available for them to use,
 so this is broadcast receiver who will intercept this communication
 and will initiate appropriate action.
 A Broadcast Receiver needs the to be made and registered
 */
public class MainActivity extends AppCompatActivity {

    IntentFilter mFilter = new IntentFilter(Intent.ACTION_TIME_TICK);

    //creation of broadcast receiver
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //register on resume
    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(mBroadcastReceiver, mFilter);
    }

    //unregister on pause
    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mBroadcastReceiver);
    }

    public void customBroadcast(View v)
    {
        if(v.getId() == R.id.button)
        {
            //creating a custom broadcast message
            Intent intent = new Intent();
            String s = getApplicationContext().toString();
            intent.setAction("s");
            intent.putExtra("message", "This is a message");
            sendBroadcast(intent);
        }
    }
}
