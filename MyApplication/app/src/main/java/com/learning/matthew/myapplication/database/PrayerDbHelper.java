package com.learning.matthew.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class for SQL interactions with the prayer database.
 *
 * Created by Matthew on 1/14/2015.
 */
public class PrayerDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Prayer.database";

    private static PrayerDbHelper instance = null;

    private PrayerDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static PrayerDbHelper getInstance(Context context)
    {
        if (instance == null)
            instance = new PrayerDbHelper(context.getApplicationContext());

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //TODO: create tables
        db.execSQL(GroupingTable.CREATE_SQL_ENTREES);
        db.execSQL(PrayersTable.CREATE_SQL_ENTREES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(GroupingTable.SQL_DELETE_ENTRIES);
        db.execSQL(PrayersTable.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    /*
    * insert a Prayer into PrayersTable
    */
    public long insertPrayer(Prayer p, long[] tag_ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PrayersTable.COLUMN_TITLE, p.getName());
        values.put(PrayersTable.COLUMN_CATEGORY, p.getCategory());
        values.put(PrayersTable.COLUMN_MESSAGE, p.getMessage());
        values.put(PrayersTable.COLUMN_COUNT, 0);
        //TODO: put other data for the table into values

        // insert row
        long prayer_id = db.insert(PrayersTable.TABLE_NAME, null, values);


        return prayer_id;
    }
}
