package com.singh.harsukh.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {



    private EditText mEditTextA, mEditTextB;
    private BindedService mBindedService;
    private boolean status;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BindedService.LocalBinder binder =(BindedService.LocalBinder) service;
            mBindedService = binder.getService();
            status = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            status = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mEditTextA = (EditText) findViewById(R.id.editText);
        mEditTextB = (EditText) findViewById(R.id.editText2);
    }

    public static Intent aIntent(Context context)
    {
        return new Intent(context, ThirdActivity.class);
    }

    public void bindService(View v)
    {
        if(v.getId() == R.id.button6)
        {
            Intent local_intent = new Intent(this, BindedService.class);
            //the context flag is passed because if a service doesn't exist it is automatically created
            bindService(local_intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            status = true;
            Toast.makeText(getApplicationContext(), "Service Binded", Toast.LENGTH_LONG).show();
        }
    }

    public void unbindService(View v)
    {
        if(v.getId() == R.id.button7)
        {
            if(status) {
                unbindService(mServiceConnection);
                status = false;
                Toast.makeText(getApplicationContext(), "Service unbinded", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "bind it first", Toast.LENGTH_LONG).show();

            }
        }
    }

    public void add(View v)
    {
        if(v.getId() == R.id.button8)
        {
            if(status)
            {
                int x = Integer.parseInt(mEditTextA.getText().toString());
                int y = Integer.parseInt(mEditTextB.getText().toString());
                int result = mBindedService.addNumbers(x,y);
                Toast.makeText(getApplicationContext(), "Result: "+ result, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "bind service first", Toast.LENGTH_LONG).show();
            }
        }
    }
}
