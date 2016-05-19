package com.singh.harsukh.asqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabaseManager sqLiteDatabaseManager;
    EditText userName, address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.username);
        address = (EditText) findViewById(R.id.address);
        sqLiteDatabaseManager = new SQLiteDatabaseManager(getApplicationContext());
    }

    public void addUser(View view){
        String userVale = userName.getText().toString();
        String addressValue = address.getText().toString();

        long id = sqLiteDatabaseManager.insertData(userVale, addressValue);
        if(id<0){
            Toast.makeText(this, "Unsuccessful", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show();
        }
    }

    public void viewDetails(View view){
        String users = sqLiteDatabaseManager.getData();
        Toast.makeText(this, users, Toast.LENGTH_LONG).show();
    }

    public void getAddress(View view){
        String userVale = userName.getText().toString();

        String users = sqLiteDatabaseManager.getAddress(userVale);
        Toast.makeText(this, users, Toast.LENGTH_LONG).show();
    }

    public void getUserId(View view){
        String userVale = userName.getText().toString();
        String addressValue = address.getText().toString();

        String users = sqLiteDatabaseManager.getUserId(userVale, addressValue);
        Toast.makeText(this, users, Toast.LENGTH_LONG).show();
    }

    public void updateUser(View view){
        String currentName = userName.getText().toString();
        String newName = address.getText().toString();

        int count = sqLiteDatabaseManager.updateName(currentName, newName);
        Toast.makeText(this, "Updated: "+count,Toast.LENGTH_LONG).show();
    }

    public void updateUserAddress(View view){
        String userValue = userName.getText().toString();
        String newAddress = address.getText().toString();
        int count = sqLiteDatabaseManager.updateAddress(userValue, newAddress);
        Toast.makeText(this, "Updated: "+count,Toast.LENGTH_LONG).show();
    }

    public void deleteUser(View view){
        String userValue = userName.getText().toString();
        int count = sqLiteDatabaseManager.deleteName(userValue);
        Toast.makeText(this, "Deleted: "+count,Toast.LENGTH_LONG).show();
    }
}
