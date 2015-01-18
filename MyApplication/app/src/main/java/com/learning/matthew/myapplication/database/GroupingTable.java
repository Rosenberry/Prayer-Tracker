package com.learning.matthew.myapplication.database;

import android.provider.BaseColumns;

/**
 * A Contract class for the database
 *
 * Created by Matthew on 1/14/2015.
 */
public class GroupingTable {
    protected static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    protected static final String TABLE_NAME = "Groups";
    protected static final String COMMA_SEP = ", ";

    // column name and data type
    protected static final String GROUPING_ID = "Grouping_ID";
    protected static final String COLUMN_GROUP_NAME = "groupName";
    protected static final String TEXT = " TEXT";

    public static final String CREATE_SQL_ENTREES =
            CREATE_TABLE + TABLE_NAME + " (" +
                    GROUPING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    COLUMN_GROUP_NAME + TEXT + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
