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
        final List<Map<String, Object>> getitem = db_machine.get_sqldata(sqlite_id);

        final TextView clock_tv = (TextView) view.findViewById(R.id.item_clock_time);
        final ToggleButton ring_btn = (ToggleButton) view.findViewById(R.id.Ring_switch_btn);
        final ToggleButton sunday_btn = (ToggleButton) view.findViewById(R.id.Sun);

        ring_btn.setText(null);
        ring_btn.setTextOn(null);
        ring_btn.setTextOff(null);
        Log.v(TAG, "Get View" + position);
        Log.v(TAG, "Get sqlite data id = " + sqlite_id);

        ring_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "SQLITE ID = " + sqlite_id);

                //get info
                Iterator it;
                it = getitem.iterator();
                while (it.hasNext())
                {
                    Object obj = it.next();
                    Log.v(TAG, obj.toString());
                }

                if (ring_btn.isChecked())
                {
                    Log.v(TAG, "RING On");
                }
                else
                {
                    Log.v(TAG, "RING Off");
                }
            }
        });

        clock_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clock_str = clock_tv.getText().toString();
                Log.v(TAG, "Click clock" + clock_str);

                Clock_timepicker.clock_setting(main_context,null, null, Clock_timepicker.REVISE_TIME);

                clock_tv.setText(count + "");
                count++;
            }
        });
        sunday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sunday_btn.isChecked())
                    Log.v(TAG, "Toggle btn On");
                else
                    Log.v(TAG, "Toggle btn Off");
            }
        });


        return view;
    }
}