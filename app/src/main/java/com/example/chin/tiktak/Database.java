package com.example.chin.tiktak;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static String TAG = "DATABASE_HELPER";
    public static final String DB_NAME = "DB_CLOCK";
    public static final int DB_VERSION = 1;
    private static SQLiteDatabase Database_instance;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_machine.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_machine.TABLE_NAME);
        onCreate(db);
    }


    public static SQLiteDatabase getDatabase_instance(Context context)
    {
        if (Database_instance == null)
        {
            Log.v(TAG, "Create!");
            Database_instance = new Database(context, DB_NAME,null, DB_VERSION).getWritableDatabase();
        }
        else
        {
            Log.v(TAG, "Already Create!");
        }

        return Database_instance;
    }

    public static void delete_DB(Context context)
    {
        context.deleteDatabase(DB_NAME);
    }
}
