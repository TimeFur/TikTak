package com.example.chin.tiktak;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public static final String DB_NAME = "DB_CLOCK_INFO";
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
            Database_instance = new Database(context, DB_NAME,null, DB_VERSION).getWritableDatabase();
        }

        return Database_instance;
    }
}
