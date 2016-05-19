package com.singh.harsukh.menusdialogsconent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mImageView = (ImageView) findViewById(R.id.imageView);
        //makes menu available for the view objects
        registerForContextMenu(mImageView);
        registerForContextMenu(mButton);
    }

    //creates the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //does stuff on the selection of an item in a menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.action_settings)
        {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_camera)
        {
            Toast.makeText(this, "Camera Starting", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.action_maps)
        {
            Toast.makeText(this, "Maps Starting", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //works only on long click
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == mButton.getId())
        {
            getMenuInflater().inflate(R.menu.image_menu, menu);
        }
        if(v.getId() == mImageView.getId())
        {
            getMenuInflater().inflate(R.menu.button_menu, menu);
        }
        menu.setHeaderTitle("Select One");
    }

    /*
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            Log.e("MainActivity", "inside context menu");
            if(v.getId() == mButton.getId())
            {
                getMenuInflater().inflate(R.menu.image_menu, menu);
            }
            if(v.getId() == mImageView.getId())
            {
                getMenuInflater().inflate(R.menu.button_menu, menu);
            }
            menu.setHeaderTitle("Select One");
        }
        */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_share){
            Toast.makeText(this, "Share Clicked", Toast.LENGTH_LONG).show();
        }
        return super.onContextItemSelected(item);
    }

    //dialogs start here
    public void showAlert(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert Dialog");
        builder.setMessage("Do you want to Kill the App");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void showProgress(View view)
    {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Updating Stuff");
        progressDialog.setIndeterminate(false);
        //if false is set for setCancelable then it will not be able
        //to terminate
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setProgress(30);
    }

    public void showCustom(View view)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);

        Button button = (Button) dialog.findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.setTitle("Custom Dialog");
        dialog.show();

    }
}
