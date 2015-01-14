package com.learning.matthew.myapplication.database;

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
        //db.execSQL();
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}
