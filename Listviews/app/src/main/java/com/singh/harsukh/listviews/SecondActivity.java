package com.singh.harsukh.listviews;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SecondActivity extends AppCompatActivity/*ListActivity*/ {
    //private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private ListView mListView;
    private String[] the_items;
    private String[] the_desc;
    private int[] images =
            {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,
                    R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_second);
        //extending ListActivity and commenting the setContentView automatically creates a listview inside the
        //the activity

        //ListView listView = getListView(); //use this to obtain the listview

        mListView = (ListView) findViewById(R.id.listViewA);


        //a listview can also be created in the xml file for viewing the list just add a list view and specify the list that
        //needs to viewed, see the reference project for this day

        Resources res = getResources(); //get the resources object

        the_items = res.getStringArray(R.array.items); //get the arrays
        the_desc = res.getStringArray(R.array.desc);

        MyAdapter adapt = new MyAdapter(getApplicationContext(), the_items,images,  the_desc);
    }

    public static Intent getIntent(Context context)
    {
        Intent intent = new Intent(context, SecondActivity.class);
        return intent;
    }
}
