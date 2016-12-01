package com.example.user.evnt;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by User on 01/12/2016.
 */
public interface EventManagment
{
    public void updateEvent(MyEvent event);
    public Collection<MyEvent> retrieveAllEvents();
    public void deleteEvent(MyEvent event);
}
