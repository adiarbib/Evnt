package com.example.user.evnt;

import com.parse.FindCallback;

import java.util.ArrayList;
import java.util.Collection;

import javax.security.auth.callback.Callback;

/**
 * Created by User on 01/12/2016.
 */
public interface EventManagment
{
    public void updateEvent(MyEvent event);
    public void retrieveEventsCallback(FindCallback<MyEvent> callback);
    public void deleteEvent(MyEvent event);
}
