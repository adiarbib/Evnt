package com.example.user.evnt;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import com.parse.FindCallback;
import com.parse.ParseException;
import java.util.Date;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String MY_PREFS_NAME = "notification_shared_preferences";
    public static final String IS_NOTIFICATION_ON_KEY = "isNotificationOn";
    public static final String WHICH_SONG_KEY = "whichSong";
    public static final String WHEN_NOTIFICATION_KEY = "whenNotification";
    public static final String WHEN_NOTIFICATION_DEFAULT = "At time of event";
    public static final String WHICH_SONG_DEFAULT = "Skyfall";
    public static final boolean IS_NOTIFICATION_ON_DEFAULT = true;
    public static EventManagment eventManagment=new ParseActions();

    String when_notification;
    String which_song;
    Spinner notif_spinner;
    Spinner ringtones_spinner;
    ArrayAdapter<CharSequence> spinner_notific_adapter;
    ArrayAdapter<CharSequence> spinner_ringtones_adapter;
    Switch addNotificationSwitch;
    Button doneBut;

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initNotifcSpinner();
        initRingtonesSpinner();
        initOtherStuff();

        doneBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainActivity = new Intent(SettingsActivity.this, MainActivity.class);
                pref = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                editor = pref.edit();
                editor.putBoolean(IS_NOTIFICATION_ON_KEY, addNotificationSwitch.isChecked());
                if(which_song.equals(""))
                {
                    which_song=WHICH_SONG_DEFAULT;
                }
                if(when_notification.equals(""))
                {
                    when_notification=WHEN_NOTIFICATION_DEFAULT;
                }
                editor.putString(WHICH_SONG_KEY, which_song);
                editor.putString(WHEN_NOTIFICATION_KEY, when_notification);
                editor.commit();

                if(pref.getBoolean(IS_NOTIFICATION_ON_KEY,IS_NOTIFICATION_ON_DEFAULT))
                {
                    //if it is on, I should return all of the current events alarms to be On.
                    setAllAlarms(SettingsActivity.this);
                }
                else
                {
                    //if notification switch is off, all current alarms should be canceled
                    cancelAllAlarms(SettingsActivity.this);
                }
                startActivity(backToMainActivity);

            }
        });
    }

    private void initOtherStuff() {
        addNotificationSwitch = (Switch) findViewById(R.id.notif_switch);
        doneBut = (Button) findViewById(R.id.done_settings_but);
    }

    private void initRingtonesSpinner() {
        ringtones_spinner = (Spinner) findViewById(R.id.ringtones_spinner);
        spinner_ringtones_adapter = ArrayAdapter.createFromResource(this, R.array.ringtones_array, android.R.layout.simple_spinner_item);
        spinner_ringtones_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ringtones_spinner.setAdapter(spinner_ringtones_adapter);
        ringtones_spinner.setOnItemSelectedListener(this);
    }

    private void initNotifcSpinner() {
        notif_spinner = (Spinner) findViewById(R.id.notifications_spinner);
        spinner_notific_adapter = ArrayAdapter.createFromResource(this, R.array.notifications_array, android.R.layout.simple_spinner_item);
        spinner_notific_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notif_spinner.setAdapter(spinner_notific_adapter);
        notif_spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.notifications_spinner) {
            when_notification = (String) parent.getItemAtPosition(pos);
        } else if (spinner.getId() == R.id.ringtones_spinner) {
            which_song = (String) parent.getItemAtPosition(pos);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public static void setAlarm(Context context,MyEvent event) {
        Date eventDate = new Date(event.getYear(), event.getMonth(), event.getDay(), event.getHour(), event.getMinute());
        SharedPreferences pref1= context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String whenNotification=pref1.getString(WHEN_NOTIFICATION_KEY,WHEN_NOTIFICATION_DEFAULT);
        //todo : check if the eventDate is the correct one.
        Long alertTime = Long.valueOf(0);
        switch (whenNotification) {
            case "At time of event":
                alertTime = eventDate.getTime();
                break;
            case "1 hour before":
                alertTime = eventDate.getTime() - 3600 * 1000;
                break;
            case "2 hours before":
                alertTime = eventDate.getTime() - 7200 * 1000;
                break;
            case "1 day before":
                alertTime = eventDate.getTime() - 86400 * 1000;
                break;
            case "2 days before":
                alertTime = eventDate.getTime() - 172800 * 1000;
                break;
            case "1 week before":
                alertTime = eventDate.getTime() - 604800 * 1000;
                break;
        }
        Intent alertIntent = new Intent(context, AlertReceiver.class);
        alertIntent.putExtra("name", event.getTitle());
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(context, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public static void setAllAlarms(final Context context) {
        eventManagment.retrieveEventsCallback(new FindCallback<MyEvent>() {
            @Override
            public void done(List<MyEvent> list, ParseException e) {
                for (int i = 0; i < list.size(); i++) {
                    setAlarm(context,list.get(i));
                }
            }
        });
    }

    public static void cancelAlarm(Context context,MyEvent event) {
        //Date eventDate = new Date(event.getYear(), event.getMonth(), event.getDay(), event.getHour(), event.getMinute());
        //todo : check if the eventDate is the correct one.
        Intent alertIntent = new Intent(context, AlertReceiver.class);
        alertIntent.putExtra("name", event.getTitle());
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(PendingIntent.getBroadcast(context, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public static void cancelAllAlarms(final Context context)
    {
        eventManagment.retrieveEventsCallback(new FindCallback<MyEvent>() {
            @Override
            public void done(List<MyEvent> list, ParseException e) {
                for(int i=0;i<list.size();i++)
                {
                    cancelAlarm(context,list.get(i));
                }
            }
        });
    }


}

