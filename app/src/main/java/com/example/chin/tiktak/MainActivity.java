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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static String TAG = "MainActivity";
    List<Map<String, Object>> Clock_data = new ArrayList<Map<String, Object>>();
    SimpleAdapter Clock_list_adapter;
    DB_machine DB_machine;

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
        DB_machine = new DB_machine(MainActivity.this);

        //-----------------------------------setting the list adapter
        Clock_data = DB_machine.getAll();
        Clock_list_adapter = new clock_list_item_adapter(
                this,
                Clock_data,
                R.layout.clock_list_item_layout,
                new String[] {  DB_machine.TIME_COLUMN,
                        DB_machine.RING_COLUMN,
                        DB_machine.SUN_COLUMN,
                        DB_machine.MON_COLUMN,
                        DB_machine.TUE_COLUMN,
                        DB_machine.WED_COLUMN,
                        DB_machine.THR_COLUMN,
                        DB_machine.FRI_COLUMN,
                        DB_machine.SAT_COLUMN,
                        DB_machine.MUSIC_COLUMN,
                        DB_machine.KEY_ID,},
                new int[] {R.id.item_clock_time,}
        );
        clock_list.setAdapter(Clock_list_adapter);

        //-----------------------------------add list click event
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clock_timepicker.clock_setting(MainActivity.this, Clock_list_adapter, Clock_data,Clock_timepicker.CREATE_TIME, -1, null);
            }
        });

        //-----------------------------------setting the trigger ringing button (test)
        Button ring_btn = (Button) findViewById(R.id.trigger_ring_btn);
        ring_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

//                DB_machine.delete_DB(MainActivity.this);


//                Date currentTime = Calendar.getInstance().getTime();
//                //(0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday)
//                Log.v(TAG,"Day = " + currentTime.getDay());

                List<Map<String, Object>> getitem;
                Iterator it;
                getitem = DB_machine.getAll();
                it = getitem.iterator();
                while (it.hasNext())
                {
                    Map<String, Object> data = (Map<String, Object>) it.next();

                    Log.v(TAG, data.toString());
                    String id = (String) data.get("_id");
                    String active_flag = (String) data.get("RING");
                    int alarm_id = Integer.parseInt(id);

                    Intent intent = new Intent(v.getContext(), ClockReceiver.class);
                    PendingIntent pi = PendingIntent.getBroadcast(v.getContext(), alarm_id, intent, PendingIntent.FLAG_ONE_SHOT);

//                    if (pi == null)
//                        Log.v(TAG, id + " = NULL, " + active_flag);
//                    else
//                        Log.v(TAG, id + " = EXIST, " + active_flag);
                }


//                Map<String, Object> item = (Map<String, Object>) DB_machine.get_sqldata(1);
//                Log.v(TAG, item.toString());
//                DB_machine.update(1, DB_machine.RING_COLUMN, "FALSE");

//                Log.v(TAG, "------------------------------------");
//                getitem = DB_machine.get_sqldata(8);
//                it = getitem.iterator();
//                while (it.hasNext())
//                {
//                    Object obj = it.next();
//                    Log.v(TAG, obj.toString());
//                }

//                Clock_timepicker.notification(MainActivity.this);

                Intent intent = new Intent(MainActivity.this, Ring_playground.class);
                startActivity(intent);
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
