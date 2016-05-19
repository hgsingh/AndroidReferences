 package com.singh.harsukh.threads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

 /*
    priorities of processes:
        1. Foreground Process (current screen)
        2. Visible Process (visible on pressing the back button)
        3. Service Process (long running process)
        4. Background Process (runs in the background minimal memory)
        5. Empty Process
  */

 /*
    Threads are through a queue called the message queue, the looper
    loops through the queue and the handlers handle the thread. Works
    in a round robin fashion.

    Content providers have the highest priority thus instantly go to the looper
  */
public class MainActivity extends AppCompatActivity {

     private ImageView mImageView;
     private Bitmap mBitmap = null;
     private Handler mHandler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mHandler = new Handler();
    }
    public void downloadImage(View v)
    {
        final String url = "http://www.reactionface.info/sites/default/files/images/1287666826226.png";
        //runnable an object that calls run as soon as it is made
        //The Runnable interface should be implemented by any class whose instances are
        // intended to be executed by a thread. The class must define a method of no arguments called run
        /*
        This interface is designed to provide a common protocol for objects that wish to execute code while they are active.
        For example, Runnable is implemented by class Thread.
        Being active simply means that a thread has been started and has not yet been stopped
         */
        Thread myThread = new Thread(new Runnable() {
            @Override
            /*
            When an object implementing interface Runnable is used to create a thread,
            starting the thread causes the object's run method to be called
            in that separately executing thread.
             */
            public void run() {
                URL downloadUrl = null; //initially set object to null
                HttpURLConnection conn = null;
                InputStream inputStream = null;
                try {
                    downloadUrl = new URL(url); //create a url object
                    conn = (HttpURLConnection) downloadUrl.openConnection();
                    //make a connection between the computer and resource (downcast)
                    inputStream = conn.getInputStream();
                    //get the input stream
                    mBitmap = BitmapFactory.decodeStream(inputStream);
                    //convert the byte stream into a bitmap
                }
                catch(MalformedURLException e)
                {
                    e.printStackTrace();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    //using handler instead
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mImageView.setImageBitmap(mBitmap);
                        }
                    });

                    //this is made because the UI thread needs to receive the information
                    //so this data is passed between the threads using the method shown.

//                    MainActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            System.out.println("it worked");
//                            mImageView.setImageBitmap(mBitmap); //set image here
//                        }
//                    });
                }
                if(conn != null)
                {
                    conn.disconnect();
                }
                if(inputStream != null)
                {
                    try {
                        inputStream.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });


        myThread.start(); //needs to be called to start
    }
}
