package com.example.user.evnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<MyEvent> eventsAdapter;
    private ListView eventsList;
    private ArrayList<MyEvent> eventsArrayList;
    private ParseQueryAdapter<MyEvent> myEventListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditEventActivity.initParse(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,EditEventActivity.class);
                startActivity(intent);
            }
        });

        eventsList=(ListView)findViewById(R.id.listOfEvents);
        eventsArrayList = new ArrayList<>();
        eventsAdapter = new ArrayAdapter<MyEvent>(this, android.R.layout.simple_list_item_1, eventsArrayList);
        eventsList.setAdapter(eventsAdapter);

    }

}
