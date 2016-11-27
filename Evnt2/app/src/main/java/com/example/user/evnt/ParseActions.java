package com.example.user.evnt;

import java.util.ArrayList;

/**
 * Created by User on 26/11/2016.
 */
public class ParseActions implements ParseActionsInterface
{

    @Override
    public void createNewEvent(int year,int month, int day, int hour, int minute, String title) {

        MyEvent newEvent=new MyEvent();

    }

    @Override
    public void updateEvent(MyEvent event) {

    }

    @Override
    public ArrayList<MyEvent> retrieveAllEvents() {
        return null;
    }

    @Override
    public void deleteEvent(MyEvent event) {

    }
}
