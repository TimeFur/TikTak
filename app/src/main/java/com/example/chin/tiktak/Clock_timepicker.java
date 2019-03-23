package com.example.chin.tiktak;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clock_timepicker {

    public static final int CREATE_TIME = 0;
    public static final int REVISE_TIME = 1;
    static String TAG = "Clock time picker";

    static DB_machine DB_machine;

    static public boolean clock_setting(final Context context,
                                        final SimpleAdapter clock_list_adapter,
                                        final List<Map<String, Object>> clock_data,
                                        final int clock_time_flag)
    {
        Log.v(TAG, "clock_setting method");
        Calendar cal = Calendar.getInstance();
        final int rightnow_hour = cal.get(Calendar.HOUR_OF_DAY);
        final int rightnow_min = cal.get(Calendar.MINUTE);

        TimePickerDialog timepicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String alarm_id;

                //Get the setting time
                switch (clock_time_flag)
                {
                    case CREATE_TIME:

                        Log.v(TAG, "clock_setting create");

                        //create the time clock item
                        String time = hourOfDay + ":" + minute;

                        Map<String, Object> item = new HashMap<String, Object>();
                        item.put(DB_machine.TIME_COLUMN, time);
                        item.put(DB_machine.RING_COLUMN, "TRUE");
                        item.put(DB_machine.SUN_COLUMN, "TRUE");
                        item.put(DB_machine.MON_COLUMN, "TRUE");
                        item.put(DB_machine.TUE_COLUMN, "TRUE");
                        item.put(DB_machine.FRI_COLUMN, "TRUE");
                        item.put(DB_machine.WED_COLUMN, "TRUE");
                        item.put(DB_machine.THR_COLUMN, "TRUE");
                        item.put(DB_machine.FRI_COLUMN, "TRUE");
                        item.put(DB_machine.SAT_COLUMN, "TRUE");
                        item.put(DB_machine.MUSIC_COLUMN, "/raw/song1.mp3");

                        DB_machine.insertitem(item);

                        //default open it
                        notification(context, hourOfDay, minute, item.get(DB_machine.KEY_ID).toString());

                        clock_data.add(item);
                        clock_list_adapter.notifyDataSetChanged();

                        break;
                    case REVISE_TIME:
                        Log.v(TAG, "clock_setting revise");
                        break;
                }
            }
        }, rightnow_hour, rightnow_min, false);
        timepicker.show();

        return true;
    }

    static public void notification(Context context, int hour, int min, String sqlite_id)
    {
//        int _id = (int) System.currentTimeMillis();
        int alarm_id = Integer.parseInt(sqlite_id);

        //Setting the clock time
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);

        //Setting intent notify
        Intent intent = new Intent(context, ClockReceiver.class);
        intent.putExtra("Sqlite_id", sqlite_id);
        PendingIntent pi = PendingIntent.getBroadcast(context, alarm_id, intent, PendingIntent.FLAG_ONE_SHOT);

        //Binding cal & intent
        AlarmManager alarm_mn = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.v(TAG, "Notify at " + cal.getTime());

        alarm_mn.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pi);
    }

    static public void cancel_clock(Context context, int id)
    {
        //Setting intent notify
        Intent intent = new Intent(context, ClockReceiver.class);

        AlarmManager alarm_mn = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_ONE_SHOT);

        alarm_mn.cancel(pi);
        Log.v(TAG, "Cancel alram id : " + id);
        pi = null;
        alarm_mn = null;
    }
}
