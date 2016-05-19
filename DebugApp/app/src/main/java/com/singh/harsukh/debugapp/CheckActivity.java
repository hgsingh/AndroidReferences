package com.singh.harsukh.debugapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivity extends AppCompatActivity {

    private static final String EXTRA_BASE = "com.singh.harsukh.debugapp";
    private static final String EXTRA_STRING_ARRAY =
            EXTRA_BASE + ".strings";
    private String[] array;
    private TextView mTextView;
    private static final String TAG = "CheckActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        mTextView = (TextView) findViewById(R.id.textView2);
        array = getIntent().getStringArrayExtra(EXTRA_STRING_ARRAY);
        System.out.println(array); //checking if intent was received
        Log.d(TAG, "inside second activity");
        String answer = getAnswer(array[0], array[1]);
        System.out.println(answer);
        Intent intent = new Intent();
        if(array[2].equals(answer))
        {
            Toast.makeText(CheckActivity.this, "Good Job",Toast.LENGTH_LONG).show();
            intent.putExtra("databack", "Good Job");
            setResult(RESULT_OK, intent);
        }
        else
        {
            Toast.makeText(CheckActivity.this, "Bad Job", Toast.LENGTH_LONG).show();
            intent.putExtra("databack", "Bad Job");
            setResult(RESULT_CANCELED, intent);
        }
        mTextView.setText(answer);
        //finish();
    }

    private String getAnswer(String a, String b)
    {
        int x = Integer.parseInt(a.toString());
        int y = Integer.parseInt(b.toString());
        int result = addition(x, y);
        return Integer.toString(result);
    }

    private int addition(int a, int b)
    {
        return (a + b);
    }

    public static Intent aIntent(Context packageContext, String[] array)
    {
        Intent intent = new Intent(packageContext, CheckActivity.class);
        intent.putExtra(EXTRA_STRING_ARRAY, array);
        return intent;
    }
}
