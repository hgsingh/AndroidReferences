package com.singh.harsukh.fragmentexplorer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends FragmentActivity implements FragmentB.Counter {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager(); //1
        Fragment fragment = fm.findFragmentById(R.id.f1);//2
        Fragment fragmentb = fm.findFragmentById(R.id.f2);
        if(fragment == null && fragmentb == null)
        {
            fragment = new FragmentExplore();
            fragmentb = new FragmentB();
            fm.beginTransaction().add(R.id.f1, fragment).commit(); //3
            fm.beginTransaction().add(R.id.f2, fragmentb).commit(); //4
        }

        /*
            fragment transactions are used to add, replace, remove or detach fragments in the fragment list. The fragment manager
            maintains a stack of fragment transactions that can be navigated. FragmentManager.beginTransaction() returns an instance of
             FragmentTransaction so that they can be chained, //3 says “Create a new fragment transaction, include
             one add operation in it, and then commit it.” The add method adds the fragment to the container (XML file) that is asking for
             the fragment, it also serves as a unique identifier for a fragment in the FragmentManager list. The fragment is obtained by checking
             if the container for the fragment will return a null value,"If you are adding multiple fragments to an activity, you would typically
create separate containers with separate IDs for each of those fragments." So if a fragment is already in the list then it will be returned.
thus the fragment is hosted by the activity.

         */
    }

    public void incrementValue(int count)
    {
        FragmentB fragmentB = (FragmentB) getFragmentManager().findFragmentById(R.id.f2);
        Log.e(TAG, "incrementing value", new Exception());
        System.out.println((fragmentB == null));
        fragmentB.setTextCount(count);
    }

}
