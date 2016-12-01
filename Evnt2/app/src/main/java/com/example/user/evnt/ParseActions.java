package com.example.user.evnt;

import java.util.ArrayList;

/**
 * Created by User on 26/11/2016.
 */
public class ParseActions implements EventManagment
{
    @Override
    public void updateEvent(MyEvent event) {
        event.saveInBackground();


    }
    @Override
    public ArrayList<MyEvent> retrieveAllEvents() {
        return null;
    }

    @Override
    public void deleteEvent(MyEvent event) {

    }
}
