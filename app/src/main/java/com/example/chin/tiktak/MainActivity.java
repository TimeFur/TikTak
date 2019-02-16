package com.example.chin.tiktak;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
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
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static String TAG = "MainActivity";

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
        clock_data.add(item);

        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("time", "18:00");
        clock_data.add(item1);

        SimpleAdapter clock_list_adapter = new clock_list_item_adapter(
                this,
                clock_data,
                R.layout.clock_list_item_layout,
                new String[] {"time", "Ring"},
                new int[] {R.id.item_clock_time}
        );

        clock_list.setAdapter(clock_list_adapter);
//        clock_list.invalidate(); // fresh data

        //-----------------------------------setting the trigger ringing button (test)
        Button ring_btn = (Button) findViewById(R.id.trigger_ring_btn);
        ring_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(TAG, "Notify~");

                //Setting the clock time
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 3);

                //Setting intent notify
                Intent intent = new Intent(MainActivity.this, ClockReceiver.class);
                intent.putExtra("msg", "clock_msg_notify");
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

                //Binding cal & intent
                AlarmManager alarm_mn = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarm_mn.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);

//                Intent intent = new Intent(MainActivity.this, Ring_playground.class);
//                startActivity(intent);
            }
        });

        //-----------------------------------add list click event
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clock_timepicker.clock_setting(MainActivity.this, Clock_timepicker.CREATE_TIME);
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
