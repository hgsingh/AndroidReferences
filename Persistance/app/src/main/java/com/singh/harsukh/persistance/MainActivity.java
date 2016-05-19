package com.singh.harsukh.persistance;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;
    private SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.editText);

        //preferences started here, is used to retrieve/store preferred data
        //File creation mode: the default mode, where the created file can only be accessed by
        // the calling application (or all applications sharing the same user ID).

        /*
        Parameters
        name
        Desired preferences file. If a preferences file by this name does not exist,
        it will be created when you retrieve an editor (SharedPreferences.edit()) and then commit changes (Editor.commit()).

        mode
        Operating mode. Use 0 or MODE_PRIVATE for the default operation, MODE_WORLD_READABLE and MODE_WORLD_WRITEABLE to control permissions.
         */
        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        //Preferences.getString uses the key "username" and tries to find the value for the key
        //if no value is found then the default value denoted by the second argument is used, in this case ""
        mEditText.setText(mPreferences.getString("username", ""));

    }

    public void saveData(View view)
    {
        //making an editor object to save the preferred data
        SharedPreferences.Editor editor = mPreferences.edit();
        //putting string into the editor with the data as key/value pair
        editor.putString("username",mEditText.getText().toString());
        //commiting changes
        editor.commit();
    }

    public void openPreferences(View view)
    {
        //this button will open the preference activity
        startActivity(PrefActivity.aIntent(MainActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean red = settings.getBoolean("red", false);
        boolean green = settings.getBoolean("green", false);
        boolean blue = settings.getBoolean("blue", false);

        StringBuffer color = new StringBuffer();
        color.append("#");
        if(red){
            color.append("FF");
        } else {
            color.append("00");
        }

        if(green){
            color.append("FF");
        } else {
            color.append("00");
        }

        if(blue){
            color.append("FF");
        } else {
            color.append("00");
        }

        View layout = findViewById(R.id.layout);
        layout.setBackgroundColor(Color.parseColor(color.toString()));
    }
}
