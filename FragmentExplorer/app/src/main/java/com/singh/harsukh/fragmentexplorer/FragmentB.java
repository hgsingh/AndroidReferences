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
import android.widget.TextView;

import org.w3c.dom.Text;

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
public class FragmentB extends Fragment {

    public static final String TAG = "Fragment";
    private TextView mTextView;
    private FrameLayout mFrameLayout;

    public void onCreate(Bundle savedInstanceState) {
        /*
        *this is public method in this class where as in a
        * Activity.onCreate(savedInstanceState); this would be a
        * protected member, further saving and restoring can also be done
        *
        *
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
        View v = inflater.inflate(R.layout.fragmentb, container, false);
        mTextView = (TextView) v.findViewById(R.id.textView);
        mFrameLayout = (FrameLayout) v.findViewById(R.id.fragLayB);
        /*
        the inflater will take a layout, a views parent specified by the container and the third
        parameter will tells the inflater whether to add the inflated view to the views parent, this is passed false because you
        will add the view in the activities code.
         */
        return v;
    }

    public FragmentB() {
        super();
    }

    public void setTextCount(int count)
    {
        mTextView.setText("Count: " + count);
        if(count % 2 == 0)
            mFrameLayout.setBackgroundColor(Color.GREEN);
        else
            mFrameLayout.setBackgroundColor(Color.BLUE);
    }

    public interface Counter
    {
        void incrementValue(int count);
    }
}
