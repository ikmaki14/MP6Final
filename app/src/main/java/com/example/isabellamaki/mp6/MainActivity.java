package com.example.isabellamaki.mp6;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar calendar = Calendar.getInstance();
        String current_date = DateFormat.getDateInstance().format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(current_date);

        Intent incomingDate = getIntent();
        String date = incomingDate.getStringExtra("date");
        textViewDate.setText(date);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
            Intent menu_intent = new Intent(this, MainActivity.class);
            startActivity(menu_intent);
            return true;
        }
        if (id == R.id.action_week) {
            Toast.makeText(this, "Week is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_month) {
            Intent month_intent = new Intent(this, Month.class);
            startActivity(month_intent);
            Toast.makeText(this, "Month is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }
//        temporary
        if (id == R.id.action_map) {
            Intent map_intent = new Intent(this, FirstFragment.class);
            startActivity(map_intent);
            Toast.makeText(this, "Map is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
