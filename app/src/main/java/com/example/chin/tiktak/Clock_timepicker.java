package com.example.chin.tiktak;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

public class Clock_timepicker {

    public static final int CREATE_TIME = 0;
    public static final int REVISE_TIME = 1;
    static String TAG = "Clock time picker";

    static public boolean clock_setting(Context context, final int clock_time_flag)
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

    static public void notification(Context context)
    {
        Log.v(TAG, "Notify~");

        //Setting the clock time
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 3);

        //Setting intent notify
        Intent intent = new Intent(context, ClockReceiver.class);
        intent.putExtra("msg", "clock_msg_notify");
        PendingIntent pi = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_ONE_SHOT);

        //Binding cal & intent
        AlarmManager alarm_mn = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm_mn.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
    }
}
