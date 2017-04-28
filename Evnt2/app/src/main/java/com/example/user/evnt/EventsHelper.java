package com.example.user.evnt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.Collections;
import java.util.List;

/**
 * Created by User on 26/04/2017.
 */
public class EventsHelper implements EventManagment {

    private Context context;
    private ParseActions parseActions;
    private LocalDatabaseProvider localDatabaseProvider;
    public EventsHelper(Context context)
    {
        this.context=context;
        parseActions=new ParseActions();
        localDatabaseProvider=new LocalDatabaseProvider(context);
    }

    /**
     * updates the event in the local database and in the Parse
     * @param event
     */

    @Override
    public void updateEvent(MyEvent event) {
        if(!isNetworkConnected()) {
            showNoInternetToast();
            return;
        }
        parseActions.updateEvent(event);
        new LocalUpdateEventTask().execute(event); //todo: think about this
    }

    /**
     * Retrieves all of the events from database and from Parse
     * @param callback
     */

    @Override
    public void retrieveEventsCallback(final FindCallback<MyEvent> callback) {
        boolean isNetworkConnected=isNetworkConnected();
        if(isNetworkConnected)
        {
            parseActions.retrieveEventsCallback(new FindCallback<MyEvent>() {
                @Override
                public void done(List<MyEvent> list, ParseException e) {
                    //change local database
                    localDatabaseProvider.deleteAllEvents();
                    Collections.sort(list);
                    for(MyEvent event:list)
                    {
                        localDatabaseProvider.addEvent(event);
                    }
                    callback.done(list,null);
                }
            });
        }
        else
        {
            localDatabaseProvider.retrieveEventsCallback(callback);
        }
    }

    /**
     * Deletes an event from local database and from Parse
     * @param event
     */

    @Override
    public void deleteEvent(MyEvent event) {
        boolean isNetworkConnected=isNetworkConnected();
        if(isNetworkConnected)
        {
            parseActions.deleteEvent(event);
            localDatabaseProvider.deleteEvent(event);
        }
        else
        {
            showNoInternetToast();
        }

    }

    /**
     * Deletes all of the events from local database and from Parse
     */

    @Override
    public void deleteAllEvents() {

        boolean isNetworkConnected=isNetworkConnected();
        if(isNetworkConnected)
        {
            parseActions.deleteAllEvents();
            localDatabaseProvider.deleteAllEvents();
        }
        else
        {
            showNoInternetToast();
        }
    }

    /**
     * Adding an event to local database and to Parse
     * @param event
     */

    @Override
    public void addEvent(MyEvent event) {
        boolean isNetworkConnected=isNetworkConnected();
        if(isNetworkConnected)
        {
            parseActions.addEvent(event);
            localDatabaseProvider.addEvent(event);
        }
        else
        {
            showNoInternetToast();
        }
    }

    /**
     * checking if the network is connected
     * @return
     */

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    /**
     * showing toast for no internet
     */

    private void showNoInternetToast() {
        Toast.makeText(context,"No Internet Connection, Can not change events",Toast.LENGTH_LONG).show();

    }

    /**
     * An AsyncTask to update in the local database while the UI thread is continuing
     */

    class LocalUpdateEventTask extends AsyncTask<MyEvent,Void,Void>
    {
        @Override
        protected Void doInBackground(MyEvent... myEvents) {
            localDatabaseProvider.updateEvent(myEvents[0]);
            return null;
        }
    }
}


