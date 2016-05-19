package com.singh.harsukh.broadcastreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by harsukh on 1/29/16.
 */
public class CustomReceiver extends BroadcastReceiver
{
    //check manifest file for the implementation of the message receiving
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("message"), Toast.LENGTH_LONG).show();
    }
}
