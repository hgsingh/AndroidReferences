package com.singh.harsukh.listviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
--A listview is a view group that displays a list of scrollable items
--Items are automatically inserted into the list using an Adapter object, the Adapter pulls content from a source (array
database query)
--the listview is placed under the xml design file under the containers
--the items (strings (string array tag)) to be displayed in the listview are contained under the strings
--the items can also be added using java code shown below
When creating an
Adapter , you provide a layout identifier. This layout is the template for filling in each
row of data. The template you create contains identifiers for particular controls to which
the Adapter assigns data. A simple layout can contain as little as a single TextView con-
trol. When making an Adapter , refer to both the layout resource and the identifier of the
TextView control. The Android SDK provides some common layout resources for use in
your application.

--to interact with the listview the MainActivity has to make an onItemClickListener
 */
public class MainActivity extends AppCompatActivity {

    private ListView listview;
    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listView);

        //adapter instance  container type of string starts here, it takes the application context, and a layout
        //file of where to "adapt" the type of object (in this case Strings)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, days);

            /*
            another layout file can be created to work with the additional data to be added to the listview
            for example the single_row xml file contains a list view that has a both a text view and imageview to go along with it
            the constructor for this adapter will take an layout that will represent the individual item resource and then which resource
            to add the item to.
         */

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.single_row, R.id.textView, days);

        //so the array adapter will create a list view that will look like a list of simple_list_item layout file
        //An ArrayAdapter binds each element of the array to a single View control within the layout resource.
        //setting the adapter here
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //downcasting of textview takes place first in the toast message (see)
                Toast.makeText(getApplicationContext(), ((TextView) view).getText() + " " + position + " " + "Clicked _id = "+ id, Toast.LENGTH_LONG).show();
            }
        });
        /*The implementation of the onItemClick() method is where all the interesting work happens. The parent parameter
        is the AdapterView where the item was clicked. This is useful if your screen has more
        than one AdapterView on it. The View parameter is the specific View within the item
        that was clicked. The position is the zero-based position within the list of items that the
        user selects. Finally, the id parameter is the value of the _id column for the particular
        item that the user selects. This is useful for querying for further information about the
        particular row of data that the item represents.*/



    }

    public void goToSecond(View view)
    {
        if(view.getId() == R.id.button)
        {
            startActivity(SecondActivity.getIntent(getApplicationContext()));
        }
    }
}
