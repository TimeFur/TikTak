package com.example.chin.tiktak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB_machine {

    private static final String TAG = "DB_MACHINE";

    public static final String TABLE_NAME = "CLOCK_ITEM";

    public static final String KEY_ID         = "_id";
    public static final String TIME_COLUMN  = "TIME";
    public static final String RING_COLUMN  = "RING";
    public static final String SUN_COLUMN   = "SUN";
    public static final String MON_COLUMN   = "MON";
    public static final String TUE_COLUMN   = "TUE";
    public static final String WED_COLUMN   = "WED";
    public static final String THR_COLUMN   = "THR";
    public static final String FRI_COLUMN   = "FRI";
    public static final String SAT_COLUMN   = "SAT";
    public static final String MUSIC_COLUMN   = "MUSIC";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TIME_COLUMN + " TEXT NOT NULL, " +
                    RING_COLUMN + " TEXT NOT NULL, " +
                    SUN_COLUMN + " TEXT NOT NULL, " +
                    MON_COLUMN + " TEXT NOT NULL, " +
                    TUE_COLUMN + " TEXT NOT NULL, " +
                    WED_COLUMN + " TEXT NOT NULL, " +
                    THR_COLUMN + " TEXT NOT NULL,  " +
                    FRI_COLUMN + " TEXT NOT NULL, " +
                    SAT_COLUMN + "  TEXT NOT NULL,  " +
                    MUSIC_COLUMN + "  TEXT " + ")";

    static private DatabaseHelper DBHelper;
    static private SQLiteDatabase DB_instance = null;

    public DB_machine(Context context)
    {
        DBHelper = new DatabaseHelper(context);
        DB_instance = DBHelper.getDatabase_instance(context);
    }

    //close database helper
    public void closedb(Context context)
    {
        if (DB_instance != null)
            DB_instance.close();
    }

    //Insert clock item
    public static void  insertitem(Map<String, Object> item)
    {
        long id = -1;
        ContentValues cv = new ContentValues();

        //put the info in cv
        cv.put(TIME_COLUMN, item.get(TIME_COLUMN).toString());
        cv.put(RING_COLUMN, item.get(RING_COLUMN).toString());
        cv.put(SUN_COLUMN, item.get(SUN_COLUMN).toString());
        cv.put(MON_COLUMN, item.get(MON_COLUMN).toString());
        cv.put(TUE_COLUMN, item.get(TUE_COLUMN).toString());
        cv.put(WED_COLUMN, item.get(WED_COLUMN).toString());
        cv.put(THR_COLUMN, item.get(THR_COLUMN).toString());
        cv.put(FRI_COLUMN, item.get(FRI_COLUMN).toString());
        cv.put(SAT_COLUMN, item.get(SAT_COLUMN).toString());
        cv.put(MUSIC_COLUMN, item.get(MUSIC_COLUMN).toString());

        //Insert to table in DB
        id = DB_instance.insert(TABLE_NAME, null, cv);
        Log.v(TAG, "Insert item = " + id);

        item.put(KEY_ID, id);
    }

    // Read all sql data
    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> result = new ArrayList<>();
        Cursor cursor = DB_instance.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public static Map<String, Object> get_sqldata(long id)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String where = KEY_ID + "=" + id;

        //search this id sqlite data
        Cursor cursor = DB_instance.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        //retrieve data
        if (cursor.moveToFirst()) {
            result = getRecord(cursor);
        }
        cursor.close();

        return result;
    }

    static private Map getRecord(Cursor cursor)
    {
        Map<String, Object> result = new HashMap<String, Object>();

        result.put(KEY_ID, cursor.getString(cursor.getColumnIndex(KEY_ID)));
        result.put(TIME_COLUMN, cursor.getString(cursor.getColumnIndex(TIME_COLUMN)));
        result.put(RING_COLUMN, cursor.getString(cursor.getColumnIndex(RING_COLUMN)));
        result.put(SUN_COLUMN, cursor.getString(cursor.getColumnIndex(SUN_COLUMN)));
        result.put(MON_COLUMN, cursor.getString(cursor.getColumnIndex(MON_COLUMN)));
        result.put(TUE_COLUMN, cursor.getString(cursor.getColumnIndex(TUE_COLUMN)));
        result.put(WED_COLUMN, cursor.getString(cursor.getColumnIndex(WED_COLUMN)));
        result.put(THR_COLUMN, cursor.getString(cursor.getColumnIndex(THR_COLUMN)));
        result.put(FRI_COLUMN, cursor.getString(cursor.getColumnIndex(FRI_COLUMN)));
        result.put(SAT_COLUMN, cursor.getString(cursor.getColumnIndex(SAT_COLUMN)));
        result.put(MUSIC_COLUMN, cursor.getString(cursor.getColumnIndex(MUSIC_COLUMN)));

        return result;
    }

    //Insert clock item
    public boolean update(long id, String column, String data)
    {
        ContentValues cv = new ContentValues();

        //put the info in cv
        cv.put(column, data);

        //Insert to table in DB
        String where = KEY_ID + "=" + id;

        return DB_instance.update(TABLE_NAME, cv, where, null) > 0;
    }

    //delete
    public void delete_DB(Context context)
    {
        DBHelper.delete_DB(context);
        Log.v(TAG, "Delete Database");
    }
}
