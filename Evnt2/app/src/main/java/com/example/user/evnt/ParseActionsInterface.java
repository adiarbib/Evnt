package com.example.user.evnt;

import android.app.usage.UsageEvents;

import java.util.ArrayList;

/**
 * Created by User on 27/11/2016.
 */
public interface ParseActionsInterface
{
    public void createNewEvent(int year,int month, int day, int hour, int minute, String title);
    public void updateEvent(MyEvent event);
    public ArrayList<MyEvent> retrieveAllEvents();
    public void deleteEvent(MyEvent event);


}
