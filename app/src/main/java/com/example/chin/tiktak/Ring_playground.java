package com.example.chin.tiktak;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
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
    ImageView ring_ground;
    Random random_obj;
    private int Panel_x, Panel_y;
    private int Target_x, Target_y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_playground);

        ring_ground = (ImageView)findViewById(R.id.iv_playground);;
        random_obj = new Random();

        //setting the touch event
        ring_ground.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float x = event.getX();
                float y = event.getY();
                
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_DOWN:
                        adjust_voice(x, y);
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
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            //link & binding
            Panel_x = ring_ground.getWidth();
            Panel_y = ring_ground.getHeight();

            Log.v(TAG, "Size x, y = " + Panel_x + ", " + Panel_y);
            Target_x = random_obj.nextInt(Panel_x - 1) + 0;
            Target_y = random_obj.nextInt(Panel_y - 1) + 0;

            Log.v(TAG, "Target x, y = " + Target_x + ", " + Target_y);

            audioPlayer(getResources().openRawResourceFd(R.raw.one));
            mp.setVolume(1, 1);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dragon);
            Bitmap bitmap = Bitmap.createBitmap(ring_ground.getWidth(),ring_ground.getHeight(),Bitmap.Config.ARGB_8888);//創建Bitmap畫布
            Paint paint = new Paint();
            paint.setColor(Color.CYAN);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawCircle(Target_x, Target_y, 10, paint);
            ring_ground.setImageBitmap(bitmap);
        }
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

    public void adjust_voice(float x, float y)
    {
        float dis_ratio = 0;
        Log.v(TAG, "Touch x, y = " + x + ", " + y + ", Target = " + Target_x + "," + Target_y);
        x = (float)Target_x - x;
        y = (float)Target_y - y;

        dis_ratio = (float) (Math.sqrt(x * x + y * y) / Math.sqrt(Panel_x * Panel_x + Panel_y * Panel_y));

        Log.v(TAG, "DOWN RATIO = " + dis_ratio);
        mp.setVolume(dis_ratio, dis_ratio);
        if (dis_ratio < 0.1)
            mp.stop();
    }
}
