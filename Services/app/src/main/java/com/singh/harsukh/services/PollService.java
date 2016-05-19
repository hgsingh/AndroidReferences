package com.singh.harsukh.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by harsukh on 1/28/16.
 */

//This is a subclass of Service that uses a worker thread to handle all start requests, one at a time.
// This is the best option if you don't require that your service handle multiple requests simultaneously.
// All you need to do is implement onHandleIntent(), which receives the intent for each start request so you can do
// the background work.
    /*
    The IntentService does the following:

Creates a default worker thread that executes all intents delivered to onStartCommand() separate from your application's main thread.
Creates a work queue that passes one intent at a time to your onHandleIntent() implementation, so you never have to worry about multi-threading.
Stops the service after all start requests have been handled, so you never have to call stopSelf().
Provides default implementation of onBind() that returns null.
Provides a default implementation of onStartCommand() that sends the intent to the work queue and then to your onHandleIntent() implementation.
     */

//a constructor is mandatory for the extending the IntentService class
public class PollService extends IntentService {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     *
     * makes a queue of intents that need to be handled
     *
     * When it receives its first command, the IntentService starts up, fires up a background thread, and
     *puts the command on a queue.
     *
     *
     *The IntentService then services each command in sequence, calling onHandleIntent(Intent) on its
     *background thread for each command. New commands that come in go to the back of the queue. When
     *there are no commands left in the queue, the service stops and is destroyed.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("IntentService", "Displaying a message");
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        long endTime = System.currentTimeMillis() + 5*1000;
        while (System.currentTimeMillis() < endTime)
        {
            /*
            If you synchronize on 'this', it means that any other thread that synchronizes
            on the same object will have to wait until you release the lock before it can proceed.
            It also means that you will have to wait, if some other thread owns the lock, for the lock to be released before you can proceed.

            synchronized blocks the next thread's call to method onHandleIntent() as long as the previous thread's execution is not finished.
            Threads can access this method one at a time. Without synchronized all threads can access this method simultaneously.
             */
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d("IntentService", "I'm awake!!!!");
    }

    public PollService()
    {
        super("Pollservice");
    }
    public static Intent aIntent(Context context)
    {
        return new Intent(context, PollService.class);
    }

    /*
    * The two integer parameters are a set of flags and a start ID. The flags are
    *used to signify whether this intent delivery is an attempt to redeliver an intent or if it is an attempt
    *to retry a delivery which never made it to (or never returned from) onStartCommand(Intent,
    *int, int) . The start ID will be different for every call to onStartCommand(Intent, int, int) ,
    *so it may be used to distinguish this command from others.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "destructo... disk!!!!", Toast.LENGTH_LONG).show();
    }
}
