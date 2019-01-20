package com.example.chin.tiktak;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class Ring_playground extends AppCompatActivity {

    String TAG = "Playground";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_playground);

        int target_x, target_y;
        int panel_x, panel_y;
        Display display_wm = getWindowManager().getDefaultDisplay();;
        Point pt_size = new Point();
        ImageView ring_ground = (ImageView)findViewById(R.id.iv_playground);;
        Random random_obj = new Random();

        //link & binding
        display_wm.getSize(pt_size);
        panel_x = pt_size.x;
        panel_y = pt_size.y;

        target_x = random_obj.nextInt(panel_x - 1) + 0;
        target_y = random_obj.nextInt(panel_y - 1) + 0;

        Log.v(TAG, "Size x, y = " + panel_x + ", " + panel_y);
        Log.v(TAG, "Target x, y = " + target_x + ", " + target_y);

        //setting the touch event
        ring_ground.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float x = event.getRawX();
                float y = event.getRawY();

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                        Log.v(TAG, "MOVE = " + x);
                        Log.v(TAG, "MOVE = " + y);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        Log.v(TAG, "DOWN = " + x);
                        Log.v(TAG, "DOWN = " + y);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.v(TAG, "UP = " + x);
                        Log.v(TAG, "UP = " + y);
                        break;
                }

                return true;
            }
        });
    }
}
