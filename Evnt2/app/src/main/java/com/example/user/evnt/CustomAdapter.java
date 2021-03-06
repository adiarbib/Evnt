package com.example.user.evnt;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A custom adapter for the listView
 */
public class CustomAdapter extends ArrayAdapter<MyEvent> {

    SimpleDateFormat df;
    String formatedDate;

    /**
     * A constructor of the adapter
     * @param context
     * @param events
     */

    public CustomAdapter(Context context, List<MyEvent> events) {
        super(context, R.layout.custom_row, events);
        df = new SimpleDateFormat("dd - MMM - yyyy", Locale.US);
    }

    /**
     * A method that creates a new view that contains that title,
     * the date, and the time of the event
     * @param position
     * @param convertView
     * @param parent
     * @return new View that contains everything I want in a row of the listView
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater eventsInflater = LayoutInflater.from(getContext());
        View customView = eventsInflater.inflate(R.layout.custom_row, parent, false);

        MyEvent singleEvent = getItem(position);
        TextView singleEventTitle = (TextView) customView.findViewById(R.id.title_custom_row);
        TextView singleEventDate = (TextView) customView.findViewById(R.id.date_custom_row);
        TextView singleEventTime = (TextView) customView.findViewById(R.id.time_custom_row);

        formatedDate=df.format(new Date(singleEvent.getYear()-1900,singleEvent.getMonth()-1,singleEvent.getDay()));

        singleEventTitle.setText(singleEvent.getTitle().toString());
        singleEventDate.setText(formatedDate);
        if(singleEvent.getMinute()>9)
        {
            singleEventTime.setText(singleEvent.getHour()+" : "+singleEvent.getMinute());
        }
        else
        {
            singleEventTime.setText(singleEvent.getHour()+" : 0"+singleEvent.getMinute());
        }

        return customView;
    }
}
