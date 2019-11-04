package com.example.chin.tiktak;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class wakeup_sleep_choice extends AppCompatActivity {

    int REMAIN_TIME = 5000;
    int SECOND_UNIT = 1000;
    static String TAG = "WAKEUP_CHOICE";
    CountDownTimer countdowntimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeup_sleep_choice);

        final TextView countdown_tv = (TextView) findViewById(R.id.countdown_textview);
        Switch close_switch = (Switch) findViewById(R.id.close_switchview);

        //Counting down flow
        countdowntimer = new CountDownTimer(REMAIN_TIME, SECOND_UNIT)
        {
            public void onTick(long millisUntilFinished) //synchronized function
            {
                countdown_tv.setText("Remain: " + millisUntilFinished / SECOND_UNIT);
            }

            public void onFinish()
            {
                //jump to play ground
                Intent intent = new Intent(wakeup_sleep_choice.this, Ring_playground.class);
                startActivity(intent);
            }
        }.start();

        //Counting down flow
        close_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true)
                {
                    Log.v(TAG, "Switch is checked");

                    //stop couting down
                    countdowntimer.cancel();

                    //jump to MainActivity
                    Intent intent = new Intent(wakeup_sleep_choice.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
