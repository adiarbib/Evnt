package com.example.user.evnt;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by User on 10/02/2017.
 */
public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        createNotifiation(context,"name of event","the amount of time that is left","Alert");

    }

    public void createNotifiation(Context context,String msg,String msgText,String msgAlert)
    {
        PendingIntent notificIntent=PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context)
                .setContentTitle(msg)
                .setTicker(msgAlert)
                .setContentText(msgText);

        mBuilder.setContentIntent(notificIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager=
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());

    }
}
