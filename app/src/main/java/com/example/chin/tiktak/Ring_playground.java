package com.example.chin.tiktak;

import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
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
    public MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_playground);

        final int target_x, target_y;
        final int panel_x, panel_y;
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

        audioPlayer(getResources().openRawResourceFd(R.raw.one));
        mp.setVolume(1, 1);
        //setting the touch event
        ring_ground.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float x = event.getRawX();
                float y = event.getRawY();
                float dis_ratio = 0;

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_DOWN:
                        x = (float)target_x - x;
                        y = (float)target_y - y;

                        dis_ratio = (float) (Math.sqrt(x * x + y * y) / Math.sqrt(panel_x * panel_x + panel_y * panel_y));
                        Log.v(TAG, "DOWN RATIO = " + dis_ratio);
                        mp.setVolume(dis_ratio, dis_ratio);
                        if (dis_ratio < 0.1)
                            mp.stop();
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

    @Override
    public void onPause()
    {
        super.onPause();
        mp.stop();
    }

    public void audioPlayer(AssetFileDescriptor fd)
    {
        mp = new MediaPlayer();
        try {
            mp.setDataSource(fd);
            mp.prepare();
            mp.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
