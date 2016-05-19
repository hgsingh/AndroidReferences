package com.singh.harsukh.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by harsukh on 1/29/16.
 */

/*
Bound
A service is "bound" when an application component binds to it by calling bindService().
A bound service offers a client-server interface that allows components to interact with the service,
send requests, get results, and even do so across processes with interprocess communication (IPC).
A bound service runs only as long as another application component is bound to it.
Multiple components can bind to the service at once, but when all of them unbind, the service is destroyed.
 */
public class BindedService extends Service {
/*
* A bound service is one that allows application components to bind to it by calling bindService()
* in order to create a long-standing connection (and generally does not allow components to
* start it by calling startService()).
*
* You should create a bound service when you want to interact with the service from activities and
* other components in your application or to expose some of your application's functionality
* to other applications, through interprocess communication (IPC).
 */
    private final IBinder mBinder = new LocalBinder();

    /*

    *To create a bound service, you must implement the onBind() callback method to return an IBinder
    * that defines the interface for communication with the service. Other application components
    * can then call bindService() to retrieve the interface and begin calling methods on the service.
    * The service lives only to serve the application component that is bound to it, so when there
    * are no components bound to the service, the system destroys it
    * (you do not need to stop a bound service in the way you must when the service is started through onStartCommand()).

    *To create a bound service, the first thing you must do is define the interface that specifies
    * how a client can communicate with the service. This interface between the service and a client
    * must be an implementation of IBinder and is what your service must return from the onBind()
    * callback method. Once the client receives the IBinder, it can begin interacting with the
    * service through that interface.

    *Multiple clients can bind to the service at once. When a client is done interacting with the service, it calls
    * unbindService() to unbind. Once there are no clients bound to the service, the system destroys the service.

     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int addNumbers(int x, int y)
    {
        return x+y;
    }

    public class LocalBinder extends Binder
    {
        public BindedService getService()
        {
            return BindedService.this;
        }
    }
}
