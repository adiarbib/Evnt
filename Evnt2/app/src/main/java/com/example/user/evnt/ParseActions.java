package com.example.user.evnt;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseQuery;

/**
 * Created by User on 26/11/2016.
 */
public class ParseActions implements EventManagment {

    @Override
    public void updateEvent(MyEvent event) {
        event.saveInBackground();
    }

    @Override
    public void retrieveEventsCallback(FindCallback<MyEvent> callback) {
        ParseQuery<MyEvent> query = ParseQuery.getQuery("MyEvent");
        query.findInBackground(callback);
    }

    @Override
    public void deleteEvent(MyEvent event) {
        event.deleteInBackground();
    }

    @Override
    public void deleteAllEvents() {

    }

    @Override
    public void addEvent(MyEvent event) {
        event.saveInBackground();
    }
}
