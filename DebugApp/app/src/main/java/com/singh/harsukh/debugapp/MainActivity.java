package com.singh.harsukh.debugapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewA;
    private TextView mTextViewB;
    private Button mButton;
    private EditText mEditText;
    private Random rand;
    private boolean focus;
    private static final String KEY_INDEX = "key_index";
    private static final String KEY_INDEXB = "key_indexb";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //inflate here
        mTextViewA = (TextView) findViewById(R.id.number1); //initialize privates here
        mTextViewB = (TextView) findViewById(R.id.number2);
        mButton = (Button) findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.textViewA);

        //initialize the values in textview A and textview B
        //set up random number generation
        rand = new Random();
        mTextViewA.setText(String.valueOf(randRange(0, 100)));
        mTextViewB.setText(String.valueOf(randRange(0, 100)));

        //set focus listener on EditText object
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    try
                    {
                        String str = mEditText.getText().toString();
                    }
                    catch(NullPointerException e)
                    {
                        Toast.makeText(MainActivity.this, "Enter a number, please", Toast.LENGTH_LONG).show();
                    }
                    if(!isNumeric(mEditText.getText().toString()))
                    {
                        Toast.makeText(MainActivity.this, "Enter a number, please",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //button listener here
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String str = mEditText.getText().toString(); //try getting the string
                    if(!isNumeric(str))
                    {
                        Toast.makeText(MainActivity.this, "Enter a valid number", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //make new activity
                        //Toast.makeText(MainActivity.this, "Good Job", Toast.LENGTH_LONG).show();
                        try {
                            //Intent intent = new Intent(MainActivity.this, CheckActivity.class); //make new activity
                            //startActivity(intent); //start it
                            Log.d(TAG, "inside try block");
                            String[] comparable = new String[3];
                            comparable[0] = mTextViewA.getText().toString();
                            comparable[1] = mTextViewB.getText().toString();
                            comparable[2] = str;
                            Intent intent = CheckActivity.aIntent(MainActivity.this, comparable);
                            Log.d(TAG, "current stack trace", new Exception() );
                            startActivity(intent);
                        }
                        catch(ActivityNotFoundException e)
                        {
                            Log.e("MainActivity", ".class file not found");
                        }
                    }
                }
                catch(NullPointerException e)
                {
                    Toast.makeText(MainActivity.this, "You must enter something before pressing GO", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private static  boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    private int randRange(int min, int max)
    {
        if(min < 0 || max < 0) return -1;
        return (rand.nextInt((max - min) + 1) + min);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Activity.RESULT_OK)
        {
            Toast.makeText(MainActivity.this, "Getting harder", Toast.LENGTH_SHORT).show();
            focus = true;
        }
        else
        {
            Toast.makeText(MainActivity.this, "Try harder", Toast.LENGTH_SHORT).show();
            focus = false;
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        mTextViewA.setText(String.valueOf(randRange(0, 100)));
        mTextViewB.setText(String.valueOf(randRange(0, 100)));
        mEditText.setText(" ");
        Log.d(TAG, "restarting");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_INDEX, mTextViewA.getText().toString());
        outState.putString(KEY_INDEXB, mTextViewB.getText().toString());
    }

    protected  void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        mTextViewA.setText(savedInstanceState.getString(KEY_INDEX));
        mTextViewB.setText(savedInstanceState.getString(KEY_INDEXB));
    }

}
