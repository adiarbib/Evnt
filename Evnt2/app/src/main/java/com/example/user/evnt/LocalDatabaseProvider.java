package com.example.user.evnt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.parse.FindCallback;

import java.util.ArrayList;

/**
 * A class that handles with SQLite api
 */
public class LocalDatabaseProvider implements EventManagment {
    private EventDbHelper eventDbHelper;

    /**
     * The constructor of the class
     * @param context
     */

    public LocalDatabaseProvider(Context context) {
        eventDbHelper = new EventDbHelper(context);
    }

    /**
     * Updates the event in the local database
     * @param event
     */

    @Override
    public void updateEvent(MyEvent event) {
        ContentValues cv = new ContentValues();
        putContentValues(event, cv);
        eventDbHelper.getWritableDatabase().update(TableCommands.TableEntries.TABLE_NAME, cv, "?=?",new String[]{
                TableCommands.TableEntries._ID,
                event.getObjectId()});
    }

    /**
     * A method that inserts values into the ContentValues
     * @param event
     * @param cv
     */

    private void putContentValues(MyEvent event, ContentValues cv) {
        cv.put(TableCommands.TableEntries._ID, event.getObjectId());
        cv.put(TableCommands.TableEntries.COLUMN_NAME_TITLE, event.getTitle());
        cv.put(TableCommands.TableEntries.COLUMN_NAME_YEAR, event.getYear());
        cv.put(TableCommands.TableEntries.COLUMN_NAME_MONTH, event.getMonth());
        cv.put(TableCommands.TableEntries.COLUMN_NAME_DAY, event.getDay());
        cv.put(TableCommands.TableEntries.COLUMN_NAME_HOUR, event.getHour());
        cv.put(TableCommands.TableEntries.COLUMN_NAME_MINUTE, event.getMinute());
    }

    /**
     * Retrieves all of the events from database (from the backup)
     * @param callback
     */

    @Override
    public void retrieveEventsCallback(FindCallback<MyEvent> callback) {
        ArrayList<MyEvent> myEvents = new ArrayList<>();

        Cursor c = eventDbHelper.getReadableDatabase().rawQuery("SELECT * FROM "+ TableCommands.TableEntries.TABLE_NAME,null);

        while (c.moveToNext()) {
            MyEvent event = new MyEvent();
            event.setObjectId(c.getString(0));
            event.setTitle(c.getString(1));
            event.setYear(c.getInt(2));
            event.setMonth(c.getInt(3));
            event.setDay(c.getInt(4));
            event.setHour(c.getInt(5));
            event.setMinute(c.getInt(6));
            myEvents.add(event);
        }

        callback.done(myEvents, null);
    }

    /**
     * Deletes an event from local database
     * @param event
     */

    @Override
    public void deleteEvent(MyEvent event) {
        eventDbHelper.getWritableDatabase().delete(TableCommands.TableEntries.TABLE_NAME,"?=?",new String[]{
                TableCommands.TableEntries._ID,
                event.getObjectId()});

    }

    /**
     * Deletes all of the events from local database
     */

    @Override
    public void deleteAllEvents() {
        eventDbHelper.getWritableDatabase().delete(TableCommands.TableEntries.TABLE_NAME,null,null);
    }

    /**
     * Adding an event to local database
     * @param event
     */

    @Override
    public void addEvent(MyEvent event) {
        ContentValues cv = new ContentValues();
        putContentValues(event, cv);
        eventDbHelper.getWritableDatabase().insert(TableCommands.TableEntries.TABLE_NAME,null,cv);
    }
}
