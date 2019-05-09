package com.example.chin.tiktak;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class clock_list_item_adapter extends SimpleAdapter {

    String TAG = "clock_list_item";
    Context main_context;
    static DB_machine db_machine;
    int count = 0;

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */

    public clock_list_item_adapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        main_context = context;
        db_machine = new DB_machine(main_context);

        Log.v(TAG, "My Adapter");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = super.getView(position, convertView, parent);

        final Map<String, Object> Clock_data = (Map<String, Object>) getItem(position);
        final long sqlite_id = (long) Clock_data.get(DB_machine.KEY_ID);
        final Map<String, Object> getitem = db_machine.get_sqldata(sqlite_id);

        final TextView clock_tv = (TextView) view.findViewById(R.id.item_clock_time);
        final ToggleButton ring_btn = (ToggleButton) view.findViewById(R.id.Ring_switch_btn);
        final ToggleButton monday_btn = (ToggleButton) view.findViewById(R.id.Mon);
        final ToggleButton tuesday_btn = (ToggleButton) view.findViewById(R.id.Tue);
        final ToggleButton wednesday_btn = (ToggleButton) view.findViewById(R.id.Wed);
        final ToggleButton thursday_btn = (ToggleButton) view.findViewById(R.id.Thr);
        final ToggleButton friday_btn = (ToggleButton) view.findViewById(R.id.Fri);
        final ToggleButton saturday_btn = (ToggleButton) view.findViewById(R.id.Sat);
        final ToggleButton sunday_btn = (ToggleButton) view.findViewById(R.id.Sun);

        ring_btn.setText(null);
        ring_btn.setTextOn(null);
        ring_btn.setTextOff(null);
        Log.v(TAG, "Get View" + position);
        Log.v(TAG, "Get sqlite data id = " + sqlite_id);

        ring_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> item;
                Log.v(TAG, "SQLITE ID = " + sqlite_id);

                String time = getitem.get(DB_machine.TIME_COLUMN).toString();
                String[] split_line = time.split(":");
                int hour = Integer.parseInt(split_line[0]);
                int min = Integer.parseInt(split_line[1]);

                if (ring_btn.isChecked())
                {
                    Log.v(TAG, "RING On");
                    db_machine.update(sqlite_id, DB_machine.RING_COLUMN, "TRUE");
                    Clock_timepicker.notification(main_context, hour, min, Integer.toString((int)sqlite_id));
                }
                else
                {
                    Log.v(TAG, "RING Off");
                    db_machine.update(sqlite_id, DB_machine.RING_COLUMN, "FALSE");
                    Clock_timepicker.cancel_clock(main_context, (int)sqlite_id);
                }

                item = db_machine.get_sqldata(sqlite_id);
                Log.v(TAG, item.toString());
            }
        });

        clock_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clock_str = clock_tv.getText().toString();
                Log.v(TAG, "Click clock" + clock_str);

                boolean pending_flag = Clock_timepicker.check_clock_pending(main_context, (int)sqlite_id);

                Log.v(TAG, "Pending = " + pending_flag);
                Clock_timepicker.clock_setting(main_context,null, null, Clock_timepicker.REVISE_TIME);
//                db_machine.update(sqlite_id, DB_machine.TIME_COLUMN, "FALSE");

                clock_tv.setText(count + "");
                count++;
            }
        });

        monday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monday_btn.isChecked())
                {
                    Log.v(TAG, "Mon Toggle btn On");
                    db_machine.update(sqlite_id, DB_machine.MON_COLUMN, "TRUE");
                }
                else
                {
                    Log.v(TAG, "Mon Toggle btn Off");
                    db_machine.update(sqlite_id, DB_machine.MON_COLUMN, "FALSE");
                }
            }
        });
        tuesday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tuesday_btn.isChecked())
                {
                    Log.v(TAG, "Tue Toggle btn On");
                    db_machine.update(sqlite_id, DB_machine.TUE_COLUMN, "TRUE");
                }
                else
                {
                    Log.v(TAG, "Tue Toggle btn Off");
                    db_machine.update(sqlite_id, DB_machine.TUE_COLUMN, "FALSE");
                }
            }
        });
        wednesday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wednesday_btn.isChecked())
                {
                    Log.v(TAG, "Wed Toggle btn On");
                    db_machine.update(sqlite_id, DB_machine.WED_COLUMN, "TRUE");
                }
                else
                {
                    Log.v(TAG, "Wed Toggle btn Off");
                    db_machine.update(sqlite_id, DB_machine.WED_COLUMN, "FALSE");
                }
            }
        });
        thursday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thursday_btn.isChecked())
                {
                    Log.v(TAG, "Thr Toggle btn On");
                    db_machine.update(sqlite_id, DB_machine.THR_COLUMN, "TRUE");
                }
                else
                {
                    Log.v(TAG, "Thr Toggle btn Off");
                    db_machine.update(sqlite_id, DB_machine.THR_COLUMN, "FALSE");
                }

            }
        });
        friday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friday_btn.isChecked())
                {
                    Log.v(TAG, "Fri Toggle btn On");
                    db_machine.update(sqlite_id, DB_machine.FRI_COLUMN, "TRUE");
                }
                else
                {
                    Log.v(TAG, "Fri Toggle btn Off");
                    db_machine.update(sqlite_id, DB_machine.FRI_COLUMN, "FALSE");
                }
            }
        });
        saturday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saturday_btn.isChecked())
                {
                    Log.v(TAG, "Sat Toggle btn On");
                    db_machine.update(sqlite_id, DB_machine.SAT_COLUMN, "TRUE");
                }
                else
                {
                    Log.v(TAG, "Sat Toggle btn Off");
                    db_machine.update(sqlite_id, DB_machine.SAT_COLUMN, "FALSE");
                }
            }
        });
        sunday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sunday_btn.isChecked())
                {
                    Log.v(TAG, "Sun Toggle btn On");
                    db_machine.update(sqlite_id, DB_machine.SUN_COLUMN, "TRUE");
                }
                else
                {
                    Log.v(TAG, "Sun Toggle btn Off");
                    db_machine.update(sqlite_id, DB_machine.SUN_COLUMN, "FALSE");
                }
            }
        });

        return view;
    }
}