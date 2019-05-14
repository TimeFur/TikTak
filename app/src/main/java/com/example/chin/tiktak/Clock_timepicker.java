package com.example.chin.tiktak;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clock_timepicker {

    public static final int CREATE_TIME = 0;
    public static final int REVISE_TIME = 1;
    public static final int CREATE_EXIST_LIST = 2;
    static String TAG = "Clock_time_picker";

    static DB_machine DB_machine;

    static public boolean clock_setting(final Context context,
                                        final SimpleAdapter clock_list_adapter,
                                        final List<Map<String, Object>> clock_data,
                                        final int clock_time_flag,
                                        final long id,
                                        final TextView tv)
    {
        Log.v(TAG, "clock_setting method");
        Calendar cal = Calendar.getInstance();
        final int rightnow_hour = cal.get(Calendar.HOUR_OF_DAY);
        final int rightnow_min = cal.get(Calendar.MINUTE);

        TimePickerDialog timepicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String time;
                String flag;
                Map<String, Object> item;

                //Get the setting time
                switch (clock_time_flag)
                {
                    case CREATE_TIME:

                        Log.v(TAG, "clock_setting new create");

                        //create the time clock item
                        time = hourOfDay + ":" + minute;

                        item = new HashMap<String, Object>();
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

                        time = hourOfDay + ":" + minute;
                        DB_machine.update(id, DB_machine.TIME_COLUMN, time);
                        item = DB_machine.get_sqldata(id);
                        flag = item.get(DB_machine.RING_COLUMN).toString();

                        if (flag.equals("TRUE"))
                        {
                            Log.v(TAG, "clock_setting revise~~~CANCEL & re notify");
                            cancel_clock(context, (int)id);
                            notification(context, hourOfDay, minute, Long.toString(id));
                            tv.setText(time);
                        }

//                        Log.v(TAG, time + ", id = " + id);
                        Log.v(TAG, "clock_setting revise");
                        break;
                }
            }
        }, rightnow_hour, rightnow_min, false);
        timepicker.show();

        return true;
    }

    static public void create_exit_list(Context context, List<Map<String, Object>> clock_data,  SimpleAdapter clock_list_adapter, Map<String, Object> data)
    {
        clock_data.add(data);
        clock_list_adapter.notifyDataSetChanged();
    }

    static public void notification(Context context, int hour, int min, String sqlite_id)
    {
        int alarm_id = Integer.parseInt(sqlite_id);

        //Setting the clock time
        Calendar cal = Calendar.getInstance();
        Date currentTime = Calendar.getInstance().getTime();

        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.HOUR_OF_DAY, hour);

        //Setting intent notify
        Intent intent = new Intent(context, ClockReceiver.class);
        intent.putExtra("Sqlite_id", sqlite_id);
        PendingIntent pi = PendingIntent.getBroadcast(context, alarm_id, intent, PendingIntent.FLAG_ONE_SHOT); //setting Intent to be used by other activity (FLAG_UPDATE_CURRENT)

        //Binding cal & intent
        AlarmManager alarm_mn = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (currentTime.getTime() >= cal.getTimeInMillis())
            cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);

        Log.v(TAG, "Notify at " + cal.getTime());
        alarm_mn.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pi);
    }

    static public void cancel_clock(Context context, int id)
    {
        //Setting intent notify
        Intent intent = new Intent(context, ClockReceiver.class);

        AlarmManager alarm_mn = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_ONE_SHOT);
        pi.cancel();
        alarm_mn.cancel(pi);

        Log.v(TAG, "Cancel alram id : " + id);
        pi = null;
        alarm_mn = null;
    }

    static boolean check_clock_pending(Context context, int id)
    {
        Intent intent = new Intent(context, ClockReceiver.class);
        if (PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_ONE_SHOT) == null)
            return false;
        return true;
    }
}
