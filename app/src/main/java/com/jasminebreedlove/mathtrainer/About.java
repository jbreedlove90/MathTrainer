package com.jasminebreedlove.mathtrainer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class About extends AppCompatActivity {
    TextView versionNum, soundsTV, imagesTV;
    Button rateBtn;

    /*
* Start with rating the app
* Determine if the Play Store is installed on the device
*
* */
    public void rateApp(View view)
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    } // end rateApp

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 22)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //no inspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    } // end private intent


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        versionNum = (TextView)findViewById(R.id.versionNum);
        soundsTV = (TextView)findViewById(R.id.soundsProvided);
        imagesTV = (TextView)findViewById(R.id.imagesProvided);
        rateBtn = (Button)findViewById(R.id.rateButton);

        if (soundsTV != null) {
            soundsTV.setMovementMethod(LinkMovementMethod.getInstance());
        }

        if (imagesTV != null) {
            imagesTV.setMovementMethod(LinkMovementMethod.getInstance());
        }

    } // on create

} // end class
