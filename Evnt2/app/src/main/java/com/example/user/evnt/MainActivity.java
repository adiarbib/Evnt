package com.example.user.evnt;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class shows the user all of the events and also today's events in separate.
 */

public class MainActivity extends AppCompatActivity {

    private EventsHelper eventsHelper;
    private ListView listView;
    private ArrayAdapter<MyEvent> adapter;
    public static final String CURRENT_EVENT_POSITION = "currentEventPosition";
    private Toolbar toolbar;
    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;
    private ListView todaysEventsListView;
    private ArrayAdapter<MyEvent> todaysAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        incomingCallHandler();
        eventsHelper = new EventsHelper(this);
        initListViews();
        initFab();
        deleteEvent();
        clickedOnAnEvent();
    }

    /**
     * A method to setup the two listViews : all events and today's events
     */

    private void initListViews() {
        listView = (ListView) findViewById(R.id.listOfEvents);
        List<MyEvent> list = new ArrayList<>();
        adapter = new CustomAdapter(MainActivity.this, list);
        listView.setAdapter(adapter);

        todaysEventsListView = (ListView) findViewById(R.id.todaysEventsList);
        List<MyEvent> todaysList = new ArrayList<>();
        todaysAdapter = new CustomAdapter(MainActivity.this, todaysList);
        todaysEventsListView.setAdapter(todaysAdapter);

    }

    /**
     * Deals with an event of the user clicking on an event
     */

    private void clickedOnAnEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent editIntent = new Intent(MainActivity.this, EditEventActivity.class);
                editIntent.putExtra(CURRENT_EVENT_POSITION, i);
                startActivity(editIntent);
            }
        });
    }

    /**
     * Long click on an event will delete the event from both the Parse server and
     * the local database.
     */

    private void deleteEvent() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                eventsHelper.deleteEvent((MyEvent) listView.getItemAtPosition(i));
                listViewsUpdate();
                return true;
            }
        });
    }

    /**
     * When the user receives a call, he would be reminded to tell his friends
     * about the events.
     */

    private void incomingCallHandler() {
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    Toast.makeText(getApplicationContext(), "Don't forget to tell your love ones about the events!", Toast.LENGTH_LONG).show();
                }
            }
        };
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * A function that initiates the floating action button.
     * the user can add a new event by clicking that button.
     */

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditEventActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listViewsUpdate();
    }

    /**
     * A function to update the listViews.
     */

    private void listViewsUpdate() {
        eventsHelper.retrieveEventsCallback(new FindCallback<MyEvent>() {
            @Override
            public void done(List<MyEvent> list, ParseException e) {
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                findViewById(R.id.todayLoadingPanel).setVisibility(View.GONE);
                Collections.sort(list);
                adapter.clear();
                adapter.addAll(list);
                adapter.notifyDataSetChanged();

                todaysAdapter.clear();
                for (MyEvent event : list) {
                    if (event.getDay() == Calendar.getInstance().getTime().getDate()) {
                        todaysAdapter.add(event);
                    }
                }
                todaysAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Add:
                Intent intent = new Intent(MainActivity.this, EditEventActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

}
