package com.example.user.evnt;

import android.provider.BaseColumns;

/**
 * Created by User on 26/04/2017.
 */
public class TableCommands
{
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TableEntries.TABLE_NAME + " (" +
                    TableEntries._ID + " TEXT PRIMARY KEY," +
                    TableEntries.COLUMN_NAME_TITLE + " TEXT," +
                    TableEntries.COLUMN_NAME_YEAR + " int," +
                    TableEntries.COLUMN_NAME_MONTH + " int," +
                    TableEntries.COLUMN_NAME_DAY + " int," +
                    TableEntries.COLUMN_NAME_HOUR + " int," +
                    TableEntries.COLUMN_NAME_MINUTE + " int)";

    public class TableEntries implements BaseColumns {

        public static final String TABLE_NAME = "Events";
        public static final String COLUMN_NAME_TITLE = MyEvent.TITLE_KEY;
        public static final String COLUMN_NAME_YEAR = MyEvent.YEAR_KEY;
        public static final String COLUMN_NAME_MONTH = MyEvent.MONTH_KEY;
        public static final String COLUMN_NAME_DAY = MyEvent.DAY_KEY;
        public static final String COLUMN_NAME_HOUR = MyEvent.HOUR_KEY;
        public static final String COLUMN_NAME_MINUTE = MyEvent.MINUTE_KEY;

    }
}
