package com.singh.harsukh.asyntasking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
    -- AsyncTask enamble proper use of the UI Thread
    -- It performs background operations and publishes the results
    on the UI Thread
    It is defined by three generic types
    --params
    --progress
    --result
    and four steps
    --onPreExecute --sets up the task in the UI Thread
    --doInBackground --executes the background taks
    --onProgressUpdate --send the UI thread on the progress made in the background thread
    --onPostExecute  --handles what comes after the background task is finished implemented on UI thread
    This AsyncTask is created on the UI thread. When the UI thread invokes the task’s
execute method, first the onPreExecute method is called on the UI thread. This allows
the task to initialize itself and its environment—in this case, installing the background
animation. Next the AsyncTask enqueues itself to run the doInBackground method con-
currently. When, eventually, doInBackground completes, the onPostExecute method is
invoked, once again in the UI thread

First, notice that the base class AsyncTask is abstract. The only way to use it is to create
a subclass specialized to perform some specific job (an is-a relationship, not a has-a
relationship). Typically, the subclass will be simple, and will define only a few methods.
A regard for good style and separation of concerns, suggests keeping the subclass small and delegating implementation to
the classes that own the UI and the asynchronous task, respectively.

In general, an AsyncTask takes a set of parameters and returns a result. Because the
parameters have to be passed between threads and the result returned between threads,
some handshaking is necessary to ensure thread safety. An AsyncTask is invoked by
calling its execute method with some parameters. Those parameters are eventually
passed on, by the AsyncTask mechanism, to the doInBackground method, when it runs
on a background thread. In turn, doInBackground produces a result. The AsyncTask
mechanism returns that result by passing it as the argument to doPostExecute , run in
the same thread as the original execute .

Just remember that the doInBackground method runs on a different thread!
When defining a concrete subclass of AsyncTask , you provide actual types for the type
variables Params , Progress , and Result , in the definition of AsyncTask . The first and last
of these type variables ( Params and Result ) are the types of the task parameters and the
result, respectively. We’ll get to that middle type variable in a minute.
The concrete type bound to Params is the type of the parameters to execute , and thus
the type of the parameters to doInBackground . Similarly, the concrete type bound to
Result is the type of the return value from doInBackground , and thus the type of the
parameter to onPostExecute .
 */
public class MainActivity extends AppCompatActivity {

    private static ImageView mImageView;
    private static Bitmap mBitmap = null;
    private final String url = "http://www.reactionface.info/sites/default/files/images/1287666826226.png";
    private static ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void downloadImage(View v)
    {
        Task localTask = new Task();
        localTask.execute(url);
    }

    public static class Task extends AsyncTask<String, Integer, Bitmap>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        //this function takes a string and returns a bitmap object
        @Override
        protected Bitmap doInBackground(String... params) {
            URL downloadUrl = null; //initially set object to null
            HttpURLConnection conn = null;
            InputStream inputStream = null;
            try {
                downloadUrl = new URL(params[0]); //create a url object
                conn = (HttpURLConnection) downloadUrl.openConnection();
                //make a connection between the computer and resource (downcast)
                inputStream = conn.getInputStream();
                for(int i = 0; i<101; ++i)
                {
                    publishProgress(i); //this will call the onProgressUpdate
                    Thread.sleep(100);
                }
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
            catch (InterruptedException e) {
                e.printStackTrace();
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
            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImageView.setImageBitmap(mBitmap);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
            if(values[0] == 100)
            {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}
