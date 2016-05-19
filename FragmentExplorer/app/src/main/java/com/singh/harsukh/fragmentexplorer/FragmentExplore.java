package com.singh.harsukh.fragmentexplorer;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Created by harsukh on 1/14/16.
 */
/*
 controller that interacts with a specific object
 --UI fragment can be hosted two ways
 --defining a spot in its layout for the fragment's view
    (add the fragment to the activitys layout)
 --manage the lifecycle of the fragment instance
 (add the fragment in the activitys code)
 this is the fragment instance
 */
public class FragmentExplore extends Fragment {

    private static final String TAG = "Fragment";
    private FrameLayout mFrameLayout;
    private static int count = 0;

    private FragmentB.Counter counter;

    public void onCreate(Bundle savedInstanceState) {
       /*
        *this is public method in this class where as in a
        * Activity.onCreate(savedInstanceState); this would be a
        * protected member, further saving and restoring can also be done
        */
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        /*
        *
        *
            Parameter space explanation: LayoutInflater and ViewGroup pararmeters are necessary to inflate
            layout, bundle will contain data that this method can use to recreate in a saved state.
         */
        View v = inflater.inflate(R.layout.flayout, container, false);
        mFrameLayout = (FrameLayout) v.findViewById(R.id.fragLayA);

        /*
        the inflater will take a layout, a views parent specified by the container and the third
        parameter will tells the inflater whether to add the inflated view to the views parent, this is passed false because you
        will add the view in the activities code.
         */
                Button b  = (Button) v.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++count;
                counter = (MainActivity) getActivity();
                counter.incrementValue(count);
                if(count % 2 == 0)
                    mFrameLayout.setBackgroundColor(Color.BLUE);
                else
                    mFrameLayout.setBackgroundColor(Color.GREEN);
            }
        });
        return v;
    }

    public FragmentExplore() {
        super();
    }
}
