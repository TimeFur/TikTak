package com.example.chin.tiktak;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextClock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //-----------------------------------setting setting_view
        TextClock title_tc = (TextClock) findViewById(R.id.tc_clock_view);
        ListView clock_list = (ListView) findViewById(R.id.lv_clock_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //-----------------------------------setting the list adapter
        List<Map<String, Object>> clock_data = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("time", "9:00");
        item.put("music", "000.mp3"); //music path

        SimpleAdapter clock_list_adapter = new SimpleAdapter(
                this,
                clock_data,
                R.layout.clock_list_item_layout,
                new String[] {"time"},
                new int[] {R.id.item_clock_time}
        );
        clock_list.setAdapter(clock_list_adapter);
//        clock_list.invalidate(); // fresh data

        //-----------------------------------setting the trigger ringing button (test)
        Button ring_btn = (Button) findViewById(R.id.trigger_ring_btn);
        ring_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ring_playground.class);
                startActivity(intent);
            }
        });

        //-----------------------------------add list click event
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
