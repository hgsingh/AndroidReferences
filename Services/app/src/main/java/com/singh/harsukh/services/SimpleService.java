package com.singh.harsukh.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by harsukh on 1/28/16.
 */
/*
Started
A service is "started" when an application component (such as an activity) starts it by calling startService().
Once started, a service can run in the background indefinitely, even if the component that started it is destroyed.
Usually, a started service performs a single operation and does not return a result to the caller.
For example, it might download or upload a file over the network.
When the operation is done, the service should stop itself.
 */
/*
services must be declared in the manifest before iml
<manifest ... >
  ...
  <application ... >
      <service android:name=".ExampleService" />
      ...
  </application>
</manifest>

Additionally, you can ensure that your service is available to only your app
by including the android:exported attribute and setting it to "false".
This effectively stops other apps from starting your service, even when using an explicit intent.
 */

    /*
    *This is the base class for all services.
    *When you extend this class, it's important that you create a new thread in which to do all the service's work,
    *because the service uses your application's main thread, by default,
    *which could slow the performance of any activity your application is running.
     */
public class SimpleService extends Service {

    private ServiceHandler mServiceHandler; //handler needed to pass messages between threads
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            long endTime = System.currentTimeMillis() + 5*1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(SimpleService.this, "Service Created", Toast.LENGTH_SHORT).show();

        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.

        HandlerThread ht = new HandlerThread("SERVICE_START_ARG", Process.THREAD_PRIORITY_BACKGROUND);
        ht.start();
        mServiceHandler = new ServiceHandler(ht.getLooper());//get looper to loop through the queue of threads
    }

    //this needs to be implemented for a started service

    /*
    ********************************************
    * However, because you handle each call to onStartCommand() yourself, you can perform multiple
    * requests simultaneously. That's not what this example does, but if that's what you want,
    * then you can create a new thread for each request and run them right away
    * (instead of waiting for the previous request to finish).
    * *********************************************
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_LONG).show();
        String message = intent.getStringExtra("message");
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage(); //get message from handler
        msg.arg1 = startId; //set id
        mServiceHandler.sendMessage(msg);
        return START_NOT_STICKY;
    }

    /*
    * Notice that the onStartCommand() method must return an integer. The integer is a value that describes
    * how the system should continue the service in the event that the system kills it (as discussed above,
    * the default implementation for IntentService handles this for you, though you are able to modify it).
    * The return value from onStartCommand() must be one of the following constants:
    */

    /*
    START_NOT_STICKY
    If the system kills the service after onStartCommand() returns, do not recreate the service, unless
    there are pending intents to deliver.
    This is the safest option to avoid running your service when not necessary
    and when your application can simply restart any unfinished jobs.

    START_STICKY
    If the system kills the service after onStartCommand() returns, recreate the service and call onStartCommand(),
    but do not redeliver the last intent. Instead, the system calls onStartCommand() with a null intent, unless
    there were pending intents to start the service, in which case, those intents are delivered.
    This is suitable for media players (or similar services) that are not executing commands, but running
    indefinitely and waiting for a job.

    START_REDELIVER_INTENT
    If the system kills the service after onStartCommand() returns, recreate the service and call onStartCommand()
    with the last intent that was delivered to the service. Any pending intents are delivered in turn.
    This is suitable for services that are actively performing a job that should be immediately resumed,
    such as downloading a file.

     */
    public static Intent aIntent(Context context)
    {
        return new Intent(context, SimpleService.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "destructo disk", Toast.LENGTH_LONG).show();
    }
}
