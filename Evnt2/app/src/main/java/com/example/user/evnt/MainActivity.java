package com.example.user.evnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EventManagment eventManagment;
    private ListView listView;
    private ArrayAdapter<MyEvent> adapter;

    public static final String CURRENT_EVENT_POSITION = "currentEventPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listOfEvents);
        initFab();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                eventManagment.deleteEvent((MyEvent) listView.getItemAtPosition(i));
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //todo: open up another activity to edit the event
                Intent editIntent = new Intent(MainActivity.this, EditEventActivity.class);
                editIntent.putExtra(CURRENT_EVENT_POSITION, i);
                startActivity(editIntent);
            }
        });
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.plus);
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
        eventManagment = new ParseActions();
        eventManagment.retrieveEventsCallback(new FindCallback<MyEvent>() {
            @Override
            public void done(List<MyEvent> list, ParseException e) {

                Collections.sort(list);
                adapter = new ArrayAdapter<MyEvent>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                listView.setAdapter(adapter);
            }
        });
    }
}
