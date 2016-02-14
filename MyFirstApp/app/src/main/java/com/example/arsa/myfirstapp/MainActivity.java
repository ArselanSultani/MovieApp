package com.example.arsa.myfirstapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/*
Simple app that tells yoy which player to get.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Toast myToast = Toast.makeText(getApplicationContext(), "Fucka You!", Toast.LENGTH_LONG);
                myToast.show();
            }
        });
    }

    public void pressInsult(View v){
        String[] insults = {"Messi", "Ronaldo", "Neymar", "Suarez", "Robben", "Hazard", "Ibrahimovic"
        , "Thiago Silva", "Boateng", "Pique", "De Gea", "Neuer"};
        int amountOfInsults = insults.length;

        Random random_Gen = new Random();
        int random_Int = random_Gen.nextInt(amountOfInsults);

        /*Toast myToast = Toast.makeText(getApplicationContext(), insults[random_Int], 500);
        myToast.show();*/

        TextView txtView = (TextView) findViewById(R.id.txtFIns);

        txtView.setText(insults[random_Int]);
        //txtField.addView(txtView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
