package com.example.chin.tiktak;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class clock_list_item_adapter extends SimpleAdapter {

    String TAG = "clock_list_item";
    Context main_context;
    static DB_machine db_machine;
    int count = 0;

    HashMap<String, Object> item_view_id = new HashMap<String, Object>() {
        {   put(DB_machine.MON_COLUMN, R.id.Mon);
            put(DB_machine.TUE_COLUMN, R.id.Tue);
            put(DB_machine.WED_COLUMN, R.id.Wed);
            put(DB_machine.THR_COLUMN, R.id.Thr);
            put(DB_machine.FRI_COLUMN, R.id.Fri);
            put(DB_machine.SAT_COLUMN, R.id.Sat);
            put(DB_machine.SUN_COLUMN, R.id.Sun);}};

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

    public clock_list_item_adapter(Context context, List<? extends Map<String, Object>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        main_context = context;
        db_machine = new DB_machine(main_context);

        Log.v(TAG, "My Adapter~");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = super.getView(position, convertView, parent);
        final Map<String, Object> Clock_data = (Map<String, Object>) getItem(position);
        String flag = "TRUE";
        Object id = (Object)Clock_data.get(DB_machine.KEY_ID);

        final long sqlite_id = Long.parseLong(id.toString());
        final Map<String, Object> getitem = db_machine.get_sqldata(sqlite_id);

        final TextView clock_tv = (TextView) view.findViewById(R.id.item_clock_time);
        final ToggleButton ring_btn = (ToggleButton) view.findViewById(R.id.Ring_switch_btn);
        final ImageButton select_sound_btn = (ImageButton) view.findViewById(R.id.music_select_id);

        for (final HashMap.Entry<String, Object> item : item_view_id.entrySet())
        {
            flag = getitem.get(item.getKey().toString()).toString();
            final ToggleButton btn = (ToggleButton) view.findViewById(Integer.parseInt(item.getValue().toString()));

            if (flag.equals("TRUE"))
                btn.setChecked(true);
            else
                btn.setChecked(false);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn.isChecked())
                    {
                        Log.v(TAG, item.getKey().toString() + " Toggle btn On");
                        db_machine.update(sqlite_id, item.getKey().toString(), "TRUE");
                    }
                    else
                    {
                        Log.v(TAG, item.getKey().toString() + " Toggle btn Off");
                        db_machine.update(sqlite_id, item.getKey().toString(), "FALSE");
                    }
                }
            });
        }

        //checking data to switch status
        flag = getitem.get(DB_machine.RING_COLUMN).toString();
        Log.v(TAG, "RING = " + flag);
        if (flag.equals("TRUE") == true)
            ring_btn.setChecked(true);
        else
            ring_btn.setChecked(false);

        ring_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> item;

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

//                item = db_machine.get_sqldata(sqlite_id);
//                Log.v(TAG, item.toString());
            }
        });

        clock_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clock_str = clock_tv.getText().toString();
                Log.v(TAG, "Click clock" + clock_str);

                boolean pending_flag = Clock_timepicker.check_clock_pending(main_context, (int)sqlite_id);

                Log.v(TAG, "Pending = " + pending_flag);
                Clock_timepicker.clock_setting(main_context,null, null, Clock_timepicker.REVISE_TIME, (long)sqlite_id, clock_tv);
//                clock_tv.setText(count + "");
            }
        });

        select_sound_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clock_list_control.createSelectDialog(v.getContext());
            }
        });

        return view;
    }
}