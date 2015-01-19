package com.learning.matthew.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Helper class for SQL interactions with the prayer database.
 *
 * Created by Matthew on 1/14/2015.
 */
public class PrayerDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Prayer.database";

    private static PrayerDbHelper instance = null;

    public PrayerDbHelper(Context context)
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
        Log.e("PrayerDbHelper", "tables creating..");
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
    public long insertPrayer(Prayer p) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("PrayerDbHelper", "inserting prayer..");

        ContentValues values = new ContentValues();
        values.put(PrayersTable.COLUMN_TITLE, p.getName());
        values.put(PrayersTable.COLUMN_CATEGORY, p.getCategory());
        values.put(PrayersTable.COLUMN_MESSAGE, p.getMessage());
        values.put(PrayersTable.COLUMN_COUNT, p.getNumPrayers());
        //TODO: put other data for the table into values

        // insert row
        long prayer_id = db.insert(PrayersTable.TABLE_NAME, null, values);

        return prayer_id;
    }

    /*
    * Updating a Prayer
    */
    public int updatePrayer(Prayer p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PrayersTable.COLUMN_COUNT, p.getNumPrayers());

        Log.e("PrayerDbHelper", "updating prayer..");

        // updating row
        return db.update(PrayersTable.TABLE_NAME, values, PrayersTable._ID + " = ?",
                new String[] { String.valueOf(p.getId()) });
    }

    /*
    * return a single Prayer from PrayersTable
    */
    public Prayer getPrayer(long prayer_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + PrayersTable.TABLE_NAME + " WHERE "
                + PrayersTable._ID + " = " + prayer_id;

        Log.e("PrayerDbHelper", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Prayer p = new Prayer();
        p.setId(c.getInt(c.getColumnIndex(PrayersTable._ID)));
        p.setName((c.getString(c.getColumnIndex(PrayersTable.COLUMN_TITLE))));
        p.setMessage(c.getString(c.getColumnIndex(PrayersTable.COLUMN_MESSAGE)));
        p.setCategory(c.getString(c.getColumnIndex(PrayersTable.COLUMN_CATEGORY)));
        p.setCounter(c.getInt(c.getColumnIndex(PrayersTable.COLUMN_COUNT)));

        return p;
    }

    /*
    * return a list of all Prayers
    * */
    public ArrayList<Prayer> getAllPrayers() {
        ArrayList<Prayer> prayers = new ArrayList<Prayer>();
        String selectQuery = "SELECT  * FROM " + PrayersTable.TABLE_NAME;

        Log.e("PrayerDbHelper", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Prayer p = new Prayer();
                p.setId(c.getInt(c.getColumnIndex(PrayersTable._ID)));
                p.setName((c.getString(c.getColumnIndex(PrayersTable.COLUMN_TITLE))));
                p.setMessage(c.getString(c.getColumnIndex(PrayersTable.COLUMN_MESSAGE)));
                p.setCategory(c.getString(c.getColumnIndex(PrayersTable.COLUMN_CATEGORY)));

                // adding to todo list
                prayers.add(p);
            } while (c.moveToNext());
        }

        return prayers;
    }

    /*
    * Deletes a Prayer from database
    */
    public void deletePrayer(long prayer_id) {
        Log.e("PrayerDbHelper", "deletePrayer() has been called");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PrayersTable.TABLE_NAME, PrayersTable._ID + " = ?",
                new String[] { String.valueOf(prayer_id) });
        Log.e("PrayerDbHelper", "Prayer " + prayer_id + " deleted from PrayersTable" );
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
