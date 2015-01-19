package com.learning.matthew.myapplication.database;

import android.provider.BaseColumns;

/**
 * Created by Matthew on 1/17/2015.
 */
public class PrayersTable {
    protected static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    protected static final String COMMA_SEP = ",";
    protected static final String TABLE_NAME = "Prayers";

    // column names
    protected static final String _ID = "PrayerTable_ID";
    protected static final String COLUMN_TITLE = "Title";
    protected static final String COLUMN_CATEGORY = "Category";
    protected static final String COLUMN_MESSAGE = "Message";
    protected static final String COLUMN_COUNT = "Count";
    protected static final String COLUMN_ANSWERED_PRAYER_BOOLEAN = "APboolean";
    protected static final String COLUMN_ANSWERED_PRAYER_MESSAGE = "APtext";
    protected static final String COLUMN_LINK = "GroupTableID";

    // table data types
    protected static final String INTEGER_PRIMARY_KEY_AUTO = " INTEGER PRIMARY KEY AUTOINCREMENT ";
    protected static final String TEXT_TYPE = " TEXT";
    protected static final String INTEGER_TYPE = " INTEGER";

    public static final String CREATE_SQL_ENTREES =
            CREATE_TABLE + TABLE_NAME + " (" +
                    _ID + INTEGER_PRIMARY_KEY_AUTO + COMMA_SEP +
                    COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_CATEGORY + TEXT_TYPE + COMMA_SEP +
                    COLUMN_MESSAGE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_COUNT + INTEGER_TYPE + COMMA_SEP +
                    COLUMN_ANSWERED_PRAYER_BOOLEAN + INTEGER_TYPE + COMMA_SEP +
                    COLUMN_ANSWERED_PRAYER_MESSAGE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_LINK + TEXT_TYPE + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    // must provide a long id
    public static final String SQL_GET_COUNT = "SELECT " + COLUMN_COUNT + " FROM " + TABLE_NAME + " WHERE "
            + _ID + " = " ;
}
