package com.example.user.evnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EventManagment eventManagment;
    private ArrayList eventsListFromParse;
    private ListView listView;
    private ArrayAdapter<MyEvent> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listOfEvents);
        initFab();
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
        eventsListFromParse = (ArrayList) eventManagment.retrieveAllEvents();
        adapter = new ArrayAdapter<MyEvent>(MainActivity.this, android.R.layout.simple_list_item_1, eventsListFromParse);
        listView.setAdapter(adapter);
    }
}
