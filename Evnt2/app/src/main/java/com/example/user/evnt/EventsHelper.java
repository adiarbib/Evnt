package com.example.user.evnt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

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
    @Override
    public void updateEvent(MyEvent event) {
        if(!isNetworkConnected()) {
            showNoInternetToast();
            return;
        }
        parseActions.updateEvent(event);
        new LocalUpdateEventTask().execute(event); //todo: think about this
    }

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

    private void showNoInternetToast() {
        //todo:change to Toast instead of Dialog
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("No Internet Connection.");
        builder1.setCancelable(true);

        builder1.setNeutralButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    class LocalUpdateEventTask extends AsyncTask<MyEvent,Void,Void>
    {
        @Override
        protected Void doInBackground(MyEvent... myEvents) {
            localDatabaseProvider.updateEvent(myEvents[0]);
            return null;
        }
    }
}


