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

    private static final int DATABASE_VERSION = 3;
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
    // called when the app is first installed
    public void onCreate(SQLiteDatabase db)
    {
        Log.e("PrayerDbHelper", "tables creating..");
        db.execSQL(GroupingTable.CREATE_SQL_ENTREES);
        db.execSQL(PrayersTable.CREATE_SQL_ENTREES);
    }

    // called when the app is updated and the db version is different
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
        values.put(PrayersTable.COLUMN_LINK, p.getGroup());

        // insert row
        return db.insert(PrayersTable.TABLE_NAME, null, values);
    }

    /*
    * Increase the counter for a single prayer
    */
    public int increaseCount(long prayer_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = PrayersTable.SQL_GET_COUNT + prayer_id;
        Log.e("PrayerDbHelper", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        ContentValues values = new ContentValues();
        int count = c.getInt(c.getColumnIndex(PrayersTable.COLUMN_COUNT));
        values.put(PrayersTable.COLUMN_COUNT, count+1);
        Log.e("PrayerDbHelper", "updating prayer " + prayer_id);
        // updating row
        return db.update(PrayersTable.TABLE_NAME, values, PrayersTable._ID + " = ?",
                new String[] { String.valueOf(prayer_id) });
    }

    /*
    * return the count for a single prayer
    */
    public int getPrayerCount(long prayer_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = PrayersTable.SQL_GET_COUNT + prayer_id;
        Log.e("PrayerDbHelper", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        return c.getInt(c.getColumnIndex(PrayersTable.COLUMN_COUNT));
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
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PrayersTable.TABLE_NAME, PrayersTable._ID + " = ?",
                new String[] { String.valueOf(prayer_id) });
        Log.e("PrayerDbHelper", "Prayer " + prayer_id + " deleted from PrayersTable" );
    }

    /*
    * insert a Group into PrayersTable
    */
    public long insertGroup(Group group){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("PrayerDbHelper", "inserting Group..");

        ContentValues values = new ContentValues();
        values.put(GroupingTable.COLUMN_NAME, group.getName());
        values.put(GroupingTable.COLUMN_DESCRIPTION, group.getDescription());

        // insert row
        return db.insert(GroupingTable.TABLE_NAME, null, values);
    }

    /*
    * return a single Group from the database
    */
    public Group getGroup(long group_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + GroupingTable.TABLE_NAME + " WHERE "
                + GroupingTable._ID + " = " + group_id;
        Log.e("PrayerDbHelper", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Group group = new Group();
        group.setName(c.getString(c.getColumnIndex(GroupingTable.COLUMN_NAME)));
        group.setDescription(c.getString(c.getColumnIndex(GroupingTable.COLUMN_DESCRIPTION)));
        group.setId(group_id);

        return group;
    }

    /*
    * return a list of all Groups
    * */
    public ArrayList<Group> getAllGroups() {
        ArrayList<Group> groups = new ArrayList<Group>();
        String selectQuery = "SELECT  * FROM " + GroupingTable.TABLE_NAME;
        Log.e("PrayerDbHelper", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Group group = new Group();
                group.setName((c.getString(c.getColumnIndex(GroupingTable.COLUMN_NAME))));
                group.setDescription(c.getString(c.getColumnIndex(GroupingTable.COLUMN_DESCRIPTION)));
                group.setId(c.getLong(c.getColumnIndex(GroupingTable._ID)));
                groups.add(group);
            } while (c.moveToNext());
        }
        return groups;
    }

    /*
    ** return a list of prayers associated with a group
     */
    public ArrayList<Prayer> getPrayersByGroup(long group_id){
        ArrayList<Prayer> prayers = new ArrayList<Prayer>();
        String selectQuery = "SELECT  * FROM " + PrayersTable.TABLE_NAME + " WHERE "
                + PrayersTable.COLUMN_LINK + " = " + group_id;
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
                prayers.add(p);
            } while (c.moveToNext());
        }

        return prayers;
    }

    /*
    ** return a list of prayer id's associated with a group
     */
    public ArrayList<Long> getPrayerIdsByGroup(long group_id){
        ArrayList<Long> prayer_ids = new ArrayList<Long>();
        String selectQuery = "SELECT  " + PrayersTable._ID + " FROM " + PrayersTable.TABLE_NAME + " WHERE "
                + PrayersTable.COLUMN_LINK + " = " + group_id;
        Log.e("PrayerDbHelper", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                prayer_ids.add(c.getLong(c.getColumnIndex(PrayersTable._ID)));
            } while (c.moveToNext());
        }

        return prayer_ids;
    }

    /*
    ** return a list of all Group id's
     */
    public ArrayList<Long> getGroupIds(){
        ArrayList<Long> group_ids = new ArrayList<Long>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + GroupingTable.TABLE_NAME;
        Log.e("PrayerDbHelper", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                group_ids.add(c.getLong(c.getColumnIndex(GroupingTable._ID)));
            } while (c.moveToNext());
        }

        return group_ids;
    }

    /*
    ** delete Group and all associated prayers
    ** returns number of deleted rows from the prayer table
     */
    public int deleteGroup(long group_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("PrayerDbHelper.java", "deleting prayers associated with group id " + group_id);
        int numDeleted = db.delete(PrayersTable.TABLE_NAME, PrayersTable.COLUMN_LINK + " = ?",
                new String[] { String.valueOf(group_id) });
        Log.e("PrayerDbHelper.java", "deleting group with group id " + group_id);
        db.delete(GroupingTable.TABLE_NAME, GroupingTable._ID + " = ?",
                new String[] { String.valueOf(group_id)});
        return numDeleted;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
