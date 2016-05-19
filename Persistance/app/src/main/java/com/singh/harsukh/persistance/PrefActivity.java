package com.singh.harsukh.persistance;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PrefActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PF()).commit();
    }

    public static Intent aIntent(Context aContext)
    {
        Intent intent = new Intent(aContext, PrefActivity.class);
        return intent;
    }

    public static class PF extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //load a preference from a resource file into this fragment
            //the file is loaded by right click res folder -> new resource file -> make it an xml in resource type
            //->root element is preference screen
            addPreferencesFromResource(R.xml.saved_preferences);
        }
    }
}
