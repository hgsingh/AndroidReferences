package com.singh.harsukh.advcontentproviders;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userName, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.username);
        address = (EditText) findViewById(R.id.address);

        //fetching data from the contacts content provider
//        Cursor people  = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        while(people.moveToNext())
//        {
//            int nameIndex = people.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
//            String name = people.getString(nameIndex);
//            Log.d("MainActivityContacts", name);
//        }
    }

    public void addUser(View view)
    {
        ContentValues values = new ContentValues();
        values.put(UserProvider.NAME, userName.getText().toString());
        values.put(UserProvider.ADDRESS, address.getText().toString());

        Uri uri = getContentResolver().insert(UserProvider.CONTENT_URI, values);
        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void viewDetails(View view)
    {
        Cursor c = getContentResolver().query(UserProvider.CONTENT_URI, null, null, null, null);
        if(c.moveToFirst()){
            do{
                Toast.makeText(this,
                        " " +  c.getString(c.getColumnIndex( UserProvider.NAME)) +
                                ", " +  c.getString(c.getColumnIndex( UserProvider.ADDRESS)),
                        Toast.LENGTH_LONG).show();
            } while(c.moveToNext());
        }
    }

    public void getAddress(View view)
    {
        String[] proj = {UserProvider.ADDRESS};
        String[] args = {userName.getText().toString()};
        Cursor c = getContentResolver().query(UserProvider.CONTENT_URI, proj, UserProvider.NAME+" = ?", args, null);
        if(c.moveToFirst()){
            do{
                Toast.makeText(this,
                        " " +  c.getString(c.getColumnIndex( UserProvider.ADDRESS)),
                        Toast.LENGTH_LONG).show();
            } while(c.moveToNext());

        }
    }

    public void getUserId(View view)
    {
        String[] proj = {UserProvider.UID};
        String[] args = {userName.getText().toString(), address.getText().toString()};
        Cursor c = getContentResolver().query(UserProvider.CONTENT_URI, proj, UserProvider.NAME+" = ? AND "+ UserProvider.ADDRESS+" = ?", args, null);
        if(c.moveToFirst()){
            do{
                Toast.makeText(this,
                        " " +  c.getString(c.getColumnIndex( UserProvider.UID)),
                        Toast.LENGTH_LONG).show();
            } while(c.moveToNext());

        }
    }

    public void updateUser(View view)
    {
        String currentName = userName.getText().toString();
        String newName = address.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserProvider.NAME, newName);


        String[] whereArgs = {currentName};
        int count = getContentResolver().update(UserProvider.CONTENT_URI, contentValues, UserProvider.NAME + " =? ", whereArgs);

        Toast.makeText(this, ""+count+" users Updated", Toast.LENGTH_LONG).show();
    }

    public void updateUserAddress(View view)
    {
        String theUser = userName.getText().toString();
        String newAddress = address.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserProvider.ADDRESS, newAddress);

        String[] whereArgs = {theUser};
        int count = getContentResolver().update(UserProvider.CONTENT_URI, contentValues, UserProvider.NAME + " =? ", whereArgs);
        Toast.makeText(this, ""+count+" users Updated", Toast.LENGTH_LONG).show();
    }

    public void deleteUser(View view)
    {
        String theUser = userName.getText().toString();

        String[] whereArgs = {theUser};
        int count = getContentResolver().delete(UserProvider.CONTENT_URI, UserProvider.NAME + "= ?", whereArgs);

        Toast.makeText(this, ""+count+" users Deleted", Toast.LENGTH_LONG).show();
    }
}
