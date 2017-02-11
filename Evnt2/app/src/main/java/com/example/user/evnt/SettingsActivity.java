package com.example.user.evnt;

<<<<<<< HEAD
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

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String MY_PREFS_NAME = "notification_shared_preferences";
    public static final String IS_NOTIFICATION_ON_KEY = "isNotificationOn";
    public static final String WHICH_SONG_KEY = "whichSong";
    public static final String WHEN_NOTIFICATION_KEY = "whenNotification";
    public static final String WHEN_NOTIFICATION_DEFAULT = "At time of event";
    public static final String WHICH_SONG_DEFAULT = "Skyfall";
    public static final boolean IS_NOTIFICATION_ON_DEFAULT = true;

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

=======
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {
>>>>>>> origin/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
<<<<<<< HEAD
        initNotifcSpinner();
        initRingtonesSpinner();
        initOtherLayoutStuff();

        doneBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainActivity = new Intent(SettingsActivity.this, MainActivity.class);
                pref = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                editor = pref.edit();
                editor.putBoolean(IS_NOTIFICATION_ON_KEY, addNotificationSwitch.isChecked());
                editor.putString(WHICH_SONG_KEY, which_song);
                editor.putString(WHEN_NOTIFICATION_KEY, when_notification);
                editor.commit();
                startActivity(backToMainActivity);

                //if notification switch is off, all current alarms should be canceled
                //if it is on, I should return all of the current events alarms to be On.
            }
        });
    }

    private void initOtherLayoutStuff() {
        addNotificationSwitch = (Switch) findViewById(R.id.notif_switch);
        doneBut = (Button) findViewById(R.id.done_settings_but);
    }

    private void initRingtonesSpinner() {
        ringtones_spinner = (Spinner) findViewById(R.id.ringtones_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        spinner_ringtones_adapter = ArrayAdapter.createFromResource(this,
                R.array.notifications_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        spinner_notific_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        notif_spinner.setAdapter(spinner_ringtones_adapter);
        notif_spinner.setOnItemSelectedListener(this);
    }

    private void initNotifcSpinner() {
        notif_spinner = (Spinner) findViewById(R.id.notifications_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        spinner_notific_adapter = ArrayAdapter.createFromResource(this,
                R.array.notifications_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        spinner_notific_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
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

=======


    }
>>>>>>> origin/master
}
