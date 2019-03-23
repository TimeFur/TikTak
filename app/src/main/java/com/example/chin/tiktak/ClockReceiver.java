package com.example.chin.tiktak;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class ClockReceiver extends BroadcastReceiver {
    String TAG = "ClockReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "Clock Receiver!!!");
        int day = -1;
        String switch_flag = "FALSE";

        Bundle data = intent.getExtras();

        //get sqlite to check day
        String id = (String) data.get("Sqlite_id");
        Map<String, Object> getitem = DB_machine.get_sqldata(Integer.parseInt(id));
        Log.v(TAG, getitem.toString());

        //get current day
        Date currentTime = Calendar.getInstance().getTime();
        day = currentTime.getDay();

        Log.v(TAG,"Receive time = " + currentTime.getHours() + ":" + currentTime.getMinutes() + ":" +  currentTime.getSeconds());
        Log.v(TAG,"Today is " + day); //(0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday)
        Log.v(TAG,"Setting time = " + getitem.get(DB_machine.TIME_COLUMN));
        //decide whether to notify ringing activity or not
        switch (day)
        {
            case 0:
                switch_flag = getitem.get(DB_machine.SUN_COLUMN).toString();
                break;
            case 1:
                switch_flag = getitem.get(DB_machine.MON_COLUMN).toString();
                break;
            case 2:
                switch_flag = getitem.get(DB_machine.TUE_COLUMN).toString();
                break;
            case 3:
                switch_flag = getitem.get(DB_machine.WED_COLUMN).toString();
                break;
            case 4:
                switch_flag = getitem.get(DB_machine.THR_COLUMN).toString();
                break;
            case 5:
                switch_flag = getitem.get(DB_machine.FRI_COLUMN).toString();
                break;
            case 6:
                switch_flag = getitem.get(DB_machine.SAT_COLUMN).toString();
                break;
        }
        Log.v(TAG,"SWITCH = " + switch_flag);

        //Setting for next time
        
        if (switch_flag.equals("TRUE"))
        {
            Intent ring_intent = new Intent(context, Ring_playground.class);
            context.startActivity(ring_intent);
        }
    }
}
