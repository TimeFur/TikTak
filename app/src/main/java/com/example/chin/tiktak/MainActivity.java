package com.example.chin.tiktak;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextClock;
import java.util.ArrayList;
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
        toolbar.setTitle(""); //set Empty title
        TextClock title_tc = (TextClock) findViewById(R.id.toolbar_tc_clock_view);
        setSupportActionBar(toolbar);

        //-----------------------------------setting setting_view
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
        clock_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final int del_position = position;
                final String listitem_id = (String)Clock_data.get(position).get(DB_machine.KEY_ID);

                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.listitem_menu, popupMenu.getMenu());
                popupMenu.show();

                Log.v(TAG, "Long click at " + position);
                Log.v(TAG, "Click del: " + listitem_id);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Log.v(TAG, "Click del: " + listitem_id);

                        //remove this item
                        Clock_data.remove(del_position);
                        Clock_list_adapter.notifyDataSetChanged();

                        //notify db remove
                        DB_machine.deleteEntry(listitem_id);

                        return false;
                    }
                });
//                Clock_data.remove(position);
//                Clock_list_adapter.notifyDataSetChanged();

                return true;
            }
        });
        //-----------------------------------add tab click event
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clock_timepicker.clock_setting(MainActivity.this, Clock_list_adapter, Clock_data, Clock_timepicker.CREATE_TIME, -1, null);
            }
        });

        //-----------------------------------setting the trigger ringing button (test)
        Button ring_btn = (Button) findViewById(R.id.trigger_ring_btn);
        ring_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_test(5);
            }
        });
    }

    void createSelectDialog(Context context)
    {
        AlertDialog.Builder alert_page = new AlertDialog.Builder(context);
        LayoutInflater layout = LayoutInflater.from(context);
        View v = layout.inflate(R.layout.select_music_layout, null);

        alert_page.setMessage("Select song");       //set title
        alert_page.setView(v);

        AlertDialog dialog = alert_page.create();   //create
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    void btn_test(int test_item)
    {
        List<Map<String, Object>> getitem = DB_machine.getAll();
        Iterator it = getitem.iterator();
        Intent intent;

        switch (test_item)
        {
            case 0:
                while (it.hasNext())
                {
                    Map<String, Object> data = (Map<String, Object>)it.next();
                    String getstatus = "";

                    Log.v(TAG, data.toString());
                    getstatus = (String)data.get(DB_machine.RING_COLUMN);

                }
                break;
            case 1:
                DB_machine.delete_DB(MainActivity.this);
                break;
            case 2:
                Map<String, Object> item = DB_machine.get_sqldata(1);
                for (Map.Entry<String, Object> entry: item.entrySet())
                {
                    Log.v(TAG, entry.getKey() + " : " + entry.getValue());;
                }

                break;
            case 4:
                intent = new Intent(MainActivity.this, Ring_playground.class);
                startActivity(intent);
                break;
            case 5:
                while (it.hasNext())
                {
                    Map<String, Object> data = (Map<String, Object>)it.next();
                    String sqlite_id = (String) data.get(DB_machine.KEY_ID);
                    int alarm_id = Integer.parseInt(sqlite_id);
                    boolean flag = false;

                    intent = new Intent(this.getApplicationContext(), ClockReceiver.class);
                    flag = (PendingIntent.getBroadcast(this.getApplicationContext(), alarm_id, intent, PendingIntent.FLAG_ONE_SHOT) != null);
                    Log.v(TAG, "SQLITE " + sqlite_id + " = " + flag);;
                }
                break;
        }

    }
}
