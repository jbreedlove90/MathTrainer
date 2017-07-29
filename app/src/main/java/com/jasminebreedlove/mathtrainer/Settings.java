package com.jasminebreedlove.mathtrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class Settings extends AppCompatActivity {
    Switch switchSound;
    boolean toggleState;
    SharedPreferences pref;
    boolean soundStatus;
    TextView aboutPage;


    public void toggleSound(android.view.View view) {

        if (switchSound.isChecked()){
         //   Log.i("TOGGLE PRESS", "SOUND IS ALREADY ON, I WANT TO TURN IT OFF-FALSE");
            toggleState = true;
            switchSound.setChecked(toggleState);
            pref.edit().putBoolean("sound", toggleState).commit(); // will store data permanently

        } else {
        //    Log.i("TOGGLE PRESS", "SOUND IS OFF, I WANT TO TURN IT ON-true");
            toggleState = false;
            switchSound.setChecked(toggleState);
            pref.edit().putBoolean("sound", toggleState).commit(); // will store data permanently

        }
        Toast.makeText(this,"Settings Saved.", Toast.LENGTH_SHORT).show();

    } // end onclick



    // will get settings from Shared Preferences using this method
    public boolean getSavedSettings() {
        soundStatus = pref.getBoolean("sound", true); // get data from SharedPref
        if (pref != null) {
            switchSound.setChecked(soundStatus); // if it's not null set toggle button state to it
        }
    //    Log.i("GET SAVED SETTINGS", String.valueOf(soundStatus));
        return soundStatus;
    } // end getSavedSettings

    public void goToAboutPg(View view) {
        Intent i = new Intent(this, About.class);
        startActivity(i);
    } // end method



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        aboutPage = (TextView)findViewById(R.id.aboutText);

        switchSound = (Switch)findViewById(R.id.switchOnOffBtn);

        pref = this.getSharedPreferences("com.jasminebreedlove.mathtrainer", Context.MODE_PRIVATE);

        getSavedSettings(); // call this method every time the activity starts

    } // end onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    } // end OnCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    } // end onOptionsItemSelected


} // end whole class
