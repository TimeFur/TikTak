package com.example.chin.tiktak;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextClock;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //-----------------------------------setting setting_view
        TextClock title_tc = (TextClock) findViewById(R.id.tc_clock_view);
        final ListView clock_list = (ListView) findViewById(R.id.lv_clock_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //-----------------------------------setting the list adapter
        List<Map<String, Object>> clock_data = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<String, Object>();

        item.put("time", "9:01");
        item.put("Ring", "Ring");
        item.put("music", "000.mp3"); //music path
        clock_data.add(item);
        clock_data.add(item);

        SimpleAdapter clock_list_adapter = new SimpleAdapter(
                this,
                clock_data,
                R.layout.clock_list_item_layout,
                new String[] {"time", "Ring"},
                new int[] {R.id.item_clock_time, R.id.Ring_switch_btn}
        );

        clock_list.setAdapter(clock_list_adapter);
        clock_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
                Log.v(TAG, position + "");

                count++;
                item.put("time", count + "");

                String time = (String)item.get("time");
                String Ring = (String)item.get("Ring");

                Log.v(TAG, Ring);
                Log.v(TAG, time);
            }
        });
//        clock_list.setOnItemClickListener(new clock_list_item_click_function());

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

    public class clock_list_item_click_function implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
            Log.v(TAG, position + "");

            String time = (String)item.get("time");
            //ToggleButton Ring_btn = (ToggleButton)item.get("Ring");

            //Log.v(TAG, Ring_btn.getWidth() + "");
            Log.v(TAG, time);
        }
    }
}
