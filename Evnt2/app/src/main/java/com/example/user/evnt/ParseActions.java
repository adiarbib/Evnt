package com.example.user.evnt;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26/11/2016.
 */
public class ParseActions implements EventManagment {

    ArrayList<MyEvent> eventList;

    @Override
    public void updateEvent(MyEvent event) {
        event.saveInBackground();
    }

    @Override
    public List<MyEvent> retrieveAllEvents() {
        eventList = new ArrayList<>();
        ParseQuery<MyEvent> query = ParseQuery.getQuery("MyEvent");
        query.findInBackground(new FindCallback<MyEvent>() {
            public void done(List<MyEvent> events, ParseException e) {
                if (e == null) {
                    eventList = (ArrayList<MyEvent>) events;
                } else {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        });
        return eventList;
        //todo : not retrieving well, I should add Log.d and check it out
    }

    @Override
    public void deleteEvent(MyEvent event) {
        event.deleteInBackground();
    }
}
