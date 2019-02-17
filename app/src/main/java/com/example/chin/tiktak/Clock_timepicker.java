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
                //Get the setting time
                switch (clock_time_flag)
                {
                    case CREATE_TIME:
                        Log.v(TAG, "clock_setting create");

                        //create the time clock item
                        Map<String, Object> item1 = new HashMap<String, Object>();
                        String t = hourOfDay + ":" + minute;
                        item1.put("TIME", t);
                        clock_data.add(item1);
                        clock_list_adapter.notifyDataSetChanged();

                        //default open it
                        notification(context, hourOfDay, minute);

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

    static public void notification(Context context, int hour, int min)
    {
        Log.v(TAG, "Notify~");
        int rightnow_hour;
        int rightnow_min;
        int rightnow_sec;

        int interval_hour = 0;
        int interval_min = 0;
        int counting_sec = 0;

        //Setting the clock time
        Calendar cal = Calendar.getInstance();
        rightnow_hour = cal.get(Calendar.HOUR_OF_DAY);
        rightnow_min = cal.get(Calendar.MINUTE);
        rightnow_sec = cal.get(Calendar.SECOND);

        Log.v(TAG, "Current time =" +  rightnow_hour + ":" + rightnow_min + ":" + rightnow_sec);
        Log.v(TAG, "Setting time =" +  hour + ":" + min + ":00");

        //evaluate the interval hour
        if (hour > rightnow_hour){
            interval_hour = hour - rightnow_hour;
        } else if (hour == rightnow_hour){
            if (min < rightnow_min)
                interval_hour = 24 - (rightnow_hour - hour);
            else
                interval_hour = hour - rightnow_hour;
        } else{
            interval_hour = 24 - (rightnow_hour - hour);
        }
        //evaluate the interval minute
        if (min > rightnow_min)
            interval_min = min - rightnow_min;
        else
            interval_min = 60 - (rightnow_min - min);

        Log.v(TAG, "interval hour =" +  interval_hour);
        Log.v(TAG, "interval min =" +  interval_min);
        counting_sec = (interval_hour * 60 * 60) + (interval_min * 60) - rightnow_sec;
        Log.v(TAG, "=> Counting = " +  counting_sec);
        cal.add(Calendar.SECOND, counting_sec);

        //Setting intent notify
        Intent intent = new Intent(context, ClockReceiver.class);
        intent.putExtra("msg", "clock_msg_notify");
        PendingIntent pi = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_ONE_SHOT);

        //Binding cal & intent
        AlarmManager alarm_mn = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm_mn.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
    }
}
