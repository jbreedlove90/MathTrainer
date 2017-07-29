package com.jasminebreedlove.mathtrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button addButton, subButton, mulButton, divButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = (Button)findViewById(R.id.chooseAdd);
        subButton = (Button)findViewById(R.id.chooseSub);
        mulButton = (Button)findViewById(R.id.chooseMultiply);
        divButton = (Button)findViewById(R.id.chooseDivide);
    } // end onCreate

    public void goToCategory(View view) {
        if (view.equals(addButton)) {
         //   Log.i("Button Press", "Add Button Pressed");
            Intent add = new Intent(this, Addition.class);
            startActivity(add);
        } else if (view.equals(subButton)) {
         //   Log.i("Button Press", "Subtract Button Pressed");
            Intent sub = new Intent(this, Subtraction.class);
            startActivity(sub);
        } else if (view.equals(mulButton)) {
        //    Log.i("Button Press", "Multiply Button Pressed");
            Intent mul = new Intent(this, Multiplication.class);
            startActivity(mul);
        } else if (view.equals(divButton)) {
        //    Log.i("Button Press", "Div Button Pressed");
            Intent div = new Intent(this, Division.class);
            startActivity(div);
        }
    } // onclick buttons method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu); // from res/menu/menu_main.xml for action bar items
        return super.onCreateOptionsMenu(menu);
    } // end OnCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent goSettings = new Intent(this, Settings.class);
            startActivity(goSettings);
        }
        return super.onOptionsItemSelected(item);
    } // end onOptionsItemSelected
}
