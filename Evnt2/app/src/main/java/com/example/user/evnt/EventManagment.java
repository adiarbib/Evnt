package com.example.user.evnt;

import com.parse.FindCallback;

/**
 * Created by User on 01/12/2016.
 */
public interface EventManagment
{
    void updateEvent(MyEvent event);
    void retrieveEventsCallback(FindCallback<MyEvent> callback);
    void deleteEvent(MyEvent event);
}
