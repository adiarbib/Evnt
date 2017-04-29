package com.example.user.evnt;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

/**
 * a class that is meant to deal with the widget.
 * it extends the AppWidgetProvider class which extends BroadcastReceiver class.
 */
public class WidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int N = appWidgetIds.length;

        /**
         * Perform this loop procedure for each App Widget that belongs to this provider
         */

        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            /**
             * Create an Intent to launch ExampleActivity
             */
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            /**
             * Get the layout for the App Widget and attach an on-click listener
             * to the button
             */

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.button, pendingIntent);

            /**
             * Tell the AppWidgetManager to perform an update on the current app widget
             */

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

}
