package com.singh.harsukh.contentproviderexample;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getContacts(View view) {
        /*
            the ContactsContract contains a content provider (Contacts) with a URI (CONTENT_URI
            the content resolver is used to return a cursor to the data
         */
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()){
            int nameIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String name = cursor.getString(nameIndex);
            Log.d("MainActivityContacts", name);

        }



    }

    public void getSMS(View view) {
        Cursor cursor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            cursor = getContentResolver().query(Telephony.Sms.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()){
                int nameIndex = cursor.getColumnIndex(Telephony.Sms.BODY);
                String text = cursor.getString(nameIndex);
                Log.d("MainActivitySMS", text);

            }
        } else {
            //its not supported
        }

    }
}
