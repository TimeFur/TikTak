package com.example.chin.tiktak;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Wayne on 2019/2/12.
 */

public class Clock_list_control extends Activity {

    static void createSelectDialog(Context context)
    {
        AlertDialog.Builder alert_page = new AlertDialog.Builder(context);
        LayoutInflater layout = LayoutInflater.from(context);
        View v = layout.inflate(R.layout.select_music_layout, null);

        alert_page.setMessage("Select song");       //set title
        alert_page.setView(v);

        AlertDialog dialog = alert_page.create();   //create
        dialog.show();
    }
}

