package com.example.user.evnt;

import com.parse.FindCallback;

/**
 * An interface for the events API
 */
public interface EventManagment
{
    /**
     * Updates the event
     * @param event
     */

    void updateEvent(MyEvent event);

    /**
     * Retrieves all of the events
     * @param callback
     */

    void retrieveEventsCallback(FindCallback<MyEvent> callback);

    /**
     * Deletes an event
     * @param event
     */

    void deleteEvent(MyEvent event);

    /**
     * Deletes all of the events
     */

    void deleteAllEvents();

    /**
     * Adding an event
     * @param event
     */

    void addEvent(MyEvent event);
}
