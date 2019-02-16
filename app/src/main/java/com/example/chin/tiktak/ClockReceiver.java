package com.example.chin.tiktak;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ClockReceiver extends BroadcastReceiver {
    String TAG = "ClockReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        if (data.get("msg").equals("clock_msg_notify"))
        {
            Log.v(TAG, "Clock Receiver!!!");
            Intent ring_intent = new Intent(context, Ring_playground.class);
            context.startActivity(ring_intent);
        }
    }
}
