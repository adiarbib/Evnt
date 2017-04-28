package com.example.user.evnt;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EventsHelper eventsHelper;
    private ListView listView;
    private ArrayAdapter<MyEvent> adapter;
    public static final String CURRENT_EVENT_POSITION = "currentEventPosition";
    private Toolbar toolbar;
    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        incomingCallHandler();
        eventsHelper = new EventsHelper(this);
        initListView();
        initFab();
        deleteEvent();
        clickedOnAnEvent();
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.listOfEvents);
        List<MyEvent> list = new ArrayList<>();
        adapter = new CustomAdapter(MainActivity.this, list);
        listView.setAdapter(adapter);
    }

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

    private void deleteEvent() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                eventsHelper.deleteEvent((MyEvent) listView.getItemAtPosition(i));
                listViewUpdate();
                return true;
            }
        });
    }

    private void incomingCallHandler() {
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if(state==TelephonyManager.CALL_STATE_RINGING)
                {
                    Toast.makeText(getApplicationContext(),"Don't forget to tell your love ones about the events!",Toast.LENGTH_LONG).show();
                }
            }
        };
        telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);
    }

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
        listViewUpdate();
    }

    private void listViewUpdate() {
        eventsHelper.retrieveEventsCallback(new FindCallback<MyEvent>() {
            @Override
            public void done(List<MyEvent> list, ParseException e) {
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                Collections.sort(list);
                adapter.clear();
                adapter.addAll(list);
                adapter.notifyDataSetChanged();
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
