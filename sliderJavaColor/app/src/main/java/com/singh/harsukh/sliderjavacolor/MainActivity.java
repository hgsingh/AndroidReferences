package com.singh.harsukh.sliderjavacolor;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar mSeekBar; //seekbar used for connecting  to linear layout
    private RelativeLayout mLayout; //layout to connect
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mTextView = (TextView) findViewById(R.id.textView);
        mLayout.setBackgroundColor(Color.RED);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            private int i = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mTextView.setText("Progress " + progress);
                if(progress % 2 ==0)
                {
                    mLayout.setBackgroundColor(Color.GREEN);
                }
                else
                {
                    mLayout.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                //unimplemented
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                //unimplemented
            }
        });
    }
}
