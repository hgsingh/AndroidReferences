package com.singh.harsukh.advcontentproviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.sql.SQLException;

/**
 * Created by harsukh on 2/2/16.
 */
//provider needs to added to the manifest
//content provider is providing an abstraction layer on how data is being retrieved and saved
public class UserProvider extends ContentProvider {
    //globals for the provider name and the url
    static final String PROVIDER_NAME = "com.singh.harsukh.provider.User";
    static final String URL = "content://"+PROVIDER_NAME +"/users";

    //URI is needed for resolving the content provider
    static final Uri CONTENT_URI = Uri.parse(URL);

    //Data that is being provided
    static final String UID = "_id";
    static final String NAME = "name";
    static final String ADDRESS = "address";

    //SQL creation
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "UsersDB";
    static final String TABLE_NAME = "USERS";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE = "CREATE TABLE "+ TABLE_NAME +" ("+ UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ NAME +" VARCHAR(255), "+ ADDRESS +" VARCHAR(255));";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
        }
    }


    /*
    onCreate
    This method provides a hook to allow your content provider to initialize itself. Any
    code you want to run just once, such as making a database connection, should
    reside in this method.
     */
    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        db = dbHelper.getWritableDatabase();
        return (db == null)?false:true;
    }

    /*
    *This function will return a cursor for a particular query built inside this function
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        if(sortOrder == null || sortOrder == "")
        {
            sortOrder = NAME;
        }
        Cursor c = queryBuilder.query(db,projection, selection, selectionArgs, null, null, sortOrder );
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return "User Database "+ uri.toString();
    }

    /*
    insert(Uri uri, ContentValues values)
    This method is called when the client code needs to insert data into the database
    your content provider is serving. Normally, the implementation for this method
    will either directly or indirectly result in a database insert operation.
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        long rowID = db.insert(TABLE_NAME,"",values); //get the rowId
        if(rowID != -1) //check if row id exists if -1 exit
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID); //appends the new id to the content uri
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = db.delete(TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = db.update(TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


}
