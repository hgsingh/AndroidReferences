package com.singh.harsukh.morethreads;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    Thread mThread;
    static Handler mHandler;
    ProgressBar mProgressBar;
    Button mButton;
    ThreadObj aThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        //as soon as the button is pressed make the progress bar visibile

        aThread = new ThreadObj();
        aThread.start();
    }

    public void makeVisible(View v)
    {
        mButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mThread = new Thread(new copyRun());
        mThread.start();
        //handler has to belong to the main thread
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                mProgressBar.setProgress(msg.arg1);
                if(msg.arg1 == 100)
                {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mButton.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    public void sendMessage(View v)
    {
        aThread.threadObjHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("Thread Name", Thread.currentThread().getName().toString());
            }
        });
    }

    class ThreadObj extends Thread
    {
        Handler threadObjHandler;
        @Override
        public void run() {
            getMainLooper().prepare(); //gets the looper
            threadObjHandler = new Handler(); //starts the handler
            getMainLooper().loop(); //loops through
        }
    }
    class copyRun implements Runnable
    {
        @Override
        public void run() {
            for(int i =0; i< 101; ++i)
            {
                Message message = Message.obtain(); //make a message object
                message.arg1 = i; //set the message
                mHandler.sendMessage(message); //sends message to UI thread handler
                try
                {
                    Thread.sleep(100);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
