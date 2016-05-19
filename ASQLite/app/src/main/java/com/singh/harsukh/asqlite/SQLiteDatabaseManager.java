package com.singh.harsukh.asqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by harsukh on 2/1/16.
 */
public class SQLiteDatabaseManager {

    SQLHelper helper;

    public SQLiteDatabaseManager(Context context){
        helper = new SQLHelper(context);
    }

    //this function will insert data into the database table
    public long insertData(String name, String address)
    {
        //returns a database object using the helper class
        SQLiteDatabase db = helper.getWritableDatabase();
        //content values are objects which can supply data
        //to the database as a set of key/value pairs in an object
        //this abstracts the function of the object
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLHelper.NAME, name);
        contentValues.put(SQLHelper.ADDRESS, address);
        //the insert function will take the table name as a parameter
        // The second parameter is the null column hack which will insert null values to specified columns
        // The contentValues param is a map. This map contains the initial column values for the row.
        // The keys should be the column names and the values the column values
        long id = db.insert(SQLHelper.TABLE_NAME, null, contentValues);
        db.close();

        return id;
    }
    public String getData(){
        //this will return a readable database object
        SQLiteDatabase db = helper.getReadableDatabase();
//        "SELECT * FROM USERS"
        //list of colums to read from
        String[] columns = {SQLHelper.UID, SQLHelper.NAME, SQLHelper.ADDRESS};

        //sets the cursor to the query table
        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();

        /*
          *
          * 1 | Vivian |123 Main St
          * 2 | Sam | 123 Someother st
          *
          */
        //retrieval
        while(cursor.moveToNext()){
            //the get column index returns an int value of the
            //specified string column name and uses it in the get string function
            int indexId = cursor.getColumnIndex(SQLHelper.UID);
            int indexName = cursor.getColumnIndex(SQLHelper.NAME);
            int indexAddress = cursor.getColumnIndex(SQLHelper.ADDRESS);
            //retrieval of data
            int uid = cursor.getInt(indexId);
            String name = cursor.getString(indexName);
            String address = cursor.getString(indexAddress);

            buffer.append(uid+":"+name+"-"+address+"\n");
        }
        return buffer.toString();
    }

    public String getAddress(String name){

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columns = {SQLHelper.ADDRESS};
        String[] args = {name};

        //the cursor object will select the the name of the args supplied and use that to find the address
        // the + ? is necessary to use the args necessary
        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, SQLHelper.NAME+" = ?", args, null, null, null);
        StringBuffer buffer = new StringBuffer();

        /*
          *
          * 1 | Vivian |123 Main St
          * 2 | Sam | 123 Someother st
          *
          */

        while(cursor.moveToNext()){

            int indexAddress = cursor.getColumnIndex(SQLHelper.ADDRESS);

            String address = cursor.getString(indexAddress);

            buffer.append(address+"\n");

        }

        return buffer.toString();

    }

    public String getUserId(String name, String address){

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columns = {SQLHelper.UID};
        String[] args = {name, address};

        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, SQLHelper.NAME+" = ? AND "+SQLHelper.ADDRESS +"= ?", args, null, null, null);
        StringBuffer buffer = new StringBuffer();

        /*
          *
          * 1 | Vivian |123 Main St
          * 2 | Sam | 123 Someother st
          *
          */

        while(cursor.moveToNext()){

            int indexId = cursor.getColumnIndex(SQLHelper.UID);
            int uid = cursor.getInt(indexId);
            buffer.append("ID:"+uid+"\n");

        }

        return buffer.toString();

    }

    //updates the name of a user in the table of the db schema
    public int updateName(String currentName, String newName){
        //gets a writable database to write to
        SQLiteDatabase db = helper.getWritableDatabase();
        //key value pairs to put into the database
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLHelper.NAME, newName);

        String[] args = {currentName};

        //writes to the specified value supplied by the args
        int count = db.update(SQLHelper.TABLE_NAME, contentValues, SQLHelper.NAME +" = ?", args);

        return count;
    }

    public int updateAddress(String userName, String newAddress){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLHelper.ADDRESS, newAddress);

        String[] args = {userName};


        int count = db.update(SQLHelper.TABLE_NAME, contentValues, SQLHelper.NAME +" = ?", args);

        return count;
    }

    public int deleteName(String userName){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] args = {userName};
        int count = db.delete(SQLHelper.TABLE_NAME, SQLHelper.NAME +" = ?", args);
        return count;
    }

    //helper object to retrieve and open the database
    //implemented for convenience
    public class SQLHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "usersDatabase";
        private static final int DATABASE_VERSION = 1;
        Context context;

        //table information
        private static final String TABLE_NAME = "USERS";
        private static final String UID = "_id";
        private static final String NAME = "Name";
        private static final String ADDRESS = "Address";
        private static final String PHONE = "Phone";

        private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + " ("+ UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ NAME +" VARCHAR(255), "+ADDRESS+" VARCHAR(255));";
        private static final String ALTER_TABLE = "ALTER TABLE "+ TABLE_NAME +" ADD COLUMN " + PHONE + " int DEFAULT 0";
        public SQLHelper(Context context) //constructor for this class
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            //the cursor factory is set null because in this case it isn't needed,
            // the other params are passed in as static finals
            //make the context a global var
            this.context = context;
        }
        @Override
        public void onCreate(SQLiteDatabase db) //creates table when the constructor is called
        {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "onCreate called", Toast.LENGTH_LONG).show();
        }

        //onUpgrade is called when the database version is changed
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(ALTER_TABLE);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        }
    }
}
