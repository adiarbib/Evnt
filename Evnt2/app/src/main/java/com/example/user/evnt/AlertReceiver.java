package com.example.user.evnt;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by User on 10/02/2017.
 */
public class AlertReceiver extends BroadcastReceiver {

    SharedPreferences pref;

    @Override
    public void onReceive(Context context, Intent intent) {
        pref = context.getSharedPreferences(SettingsActivity.MY_PREFS_NAME, context.MODE_PRIVATE);
        createNotifiation(context, intent.getStringExtra("name"), pref.getString(SettingsActivity.WHEN_NOTIFICATION_KEY, SettingsActivity.WHEN_NOTIFICATION_DEFAULT), "Alert");
    }

    public void createNotifiation(Context context, String msg, String msgText, String msgAlert) {

        PendingIntent notificIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(msg)
                .setTicker(msgAlert)
                .setContentText(msgText);

        mBuilder.setContentIntent(notificIntent);
        NotificationCompat.Builder builder = mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        String whichSong = pref.getString(SettingsActivity.WHICH_SONG_KEY, SettingsActivity.WHICH_SONG_DEFAULT);
        switch (whichSong) {
            case "Hello":
                mBuilder.setSound(Uri.parse(context.getResources().getResourceName(R.raw.hello)));
                break;
            case "Make You Feel My Love":
                mBuilder.setSound(Uri.parse(context.getResources().getResourceName(R.raw.make_you_feel_my_love)));
                break;
            case "Rumor Has It":
                mBuilder.setSound(Uri.parse(context.getResources().getResourceName(R.raw.rumor_has_it)));
                break;
            case "Send My Love":
                mBuilder.setSound(Uri.parse(context.getResources().getResourceName(R.raw.send_my_love)));
                break;
            case "Skyfall":
                mBuilder.setSound(Uri.parse(context.getResources().getResourceName(R.raw.skyfall)));
                break;
            case "Turning Tables":
                mBuilder.setSound(Uri.parse(context.getResources().getResourceName(R.raw.turning_tables)));
                break;
        }
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }
}
