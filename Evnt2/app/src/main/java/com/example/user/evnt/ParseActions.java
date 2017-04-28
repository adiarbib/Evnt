package com.example.user.evnt;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseQuery;

/**
 * Created by User on 26/11/2016.
 */
public class ParseActions implements EventManagment {

    /**
     * updates the event in the Parse server
     * @param event
     */

    @Override
    public void updateEvent(MyEvent event) {
        event.saveInBackground();
    }

    /**
     * Retrieves all of the events from Parse server
     * @param callback
     */

    @Override
    public void retrieveEventsCallback(FindCallback<MyEvent> callback) {
        ParseQuery<MyEvent> query = ParseQuery.getQuery("MyEvent");
        query.findInBackground(callback);
    }

    /**
     * Deletes an event from Parse server
     * @param event
     */

    @Override
    public void deleteEvent(MyEvent event) {
        event.deleteInBackground();
    }

    /**
     * Deletes all of the events from Parse server
     */

    @Override
    public void deleteAllEvents() {

    }

    /**
     * Adding an event to Parse server
     * @param event
     */

    @Override
    public void addEvent(MyEvent event) {
        event.saveInBackground();
    }
}
