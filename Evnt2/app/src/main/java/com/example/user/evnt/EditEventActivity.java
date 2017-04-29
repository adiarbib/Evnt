package com.example.user.evnt;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This is the activity in which the user edits
 * an existing event or creating a new one.
 */
public class EditEventActivity extends AppCompatActivity {

    EditText titleEditText;
    Button dateButton;
    Button timeButton;
    Button finishButton;
    Calendar cal;
    String formatedDate;
    String formatedTime;
    MyEvent currentEvent;
    int positionOfCurrentEvent;
    SimpleDateFormat df;
    SimpleDateFormat tf;

    int pickedHour;
    int pickedMinute;
    int pickedYear;
    int pickedMonth;
    int pickedDay;
    EventsHelper eventsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        initLayoutStuff();
        eventsHelper=new EventsHelper(this);
        positionOfCurrentEvent = getIntent().getIntExtra(MainActivity.CURRENT_EVENT_POSITION, -1);
        if (positionOfCurrentEvent != -1) {

            eventsHelper.retrieveEventsCallback(new FindCallback<MyEvent>() {
                @Override
                public void done(List<MyEvent> list, ParseException e) {
                    Collections.sort(list);
                    currentEvent = list.get(positionOfCurrentEvent);
                    initValues();

                }
            });
        } else {
            currentEvent = null;
            initValues();
        }
        initDateButton();
        initTimeButton();
        initDoneButton();
    }

    /**
     * A method that deals with the user clicking on the "Done" button.
     * It either creates a new event and uses the EventsHelper to do it
     * or updates an existing event while using the EventsHelper.
     */

    private void initDoneButton() {
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentEvent == null) {
                    MyEvent newEvent = new MyEvent();
                    newEvent.setDay(pickedDay);
                    newEvent.setMonth(pickedMonth);
                    newEvent.setYear(pickedYear);
                    newEvent.setHour(pickedHour);
                    newEvent.setMinute(pickedMinute);
                    newEvent.setTitle(titleEditText.getText().toString());
                    eventsHelper.addEvent(newEvent);
                }
                else {
                    currentEvent.setTitle(titleEditText.getText().toString());
                    eventsHelper.updateEvent(currentEvent);
                }
                Intent backToMainActivity = new Intent(EditEventActivity.this, MainActivity.class);
                startActivity(backToMainActivity);
            }
        });
    }

    /**
     * Dealing with the user clicking on the time button.
     * The method opens a new TimePickerDialog and saves
     * the user selection.
     */

    private void initTimeButton() {
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentEvent == null) {
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(EditEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            timeButton.setText(selectedHour + " : " + selectedMinute);
                            pickedHour = selectedHour;
                            pickedMinute = selectedMinute;
                        }
                    }, pickedHour, pickedMinute, true);

                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                } else {
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(EditEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            timeButton.setText(selectedHour + " : " + selectedMinute);
                            pickedHour = selectedHour;
                            currentEvent.setHour(selectedHour);
                            pickedMinute = selectedMinute;
                            currentEvent.setMinute(selectedMinute);
                        }
                    }, currentEvent.getHour(), currentEvent.getMinute(), true);

                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            }
        });
    }

    /**
     * Dealing with the user clicking on the date button.
     * The method opens a new DatePickerDialog and saves
     * the user selection.
     */

    private void initDateButton() {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentEvent == null) {
                    DatePickerDialog mDatePicker = new DatePickerDialog(EditEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedyear, int selectedmonth, int selectedday) {
                            formatedDate = df.format(new Date(selectedyear - 1900, selectedmonth, selectedday));
                            dateButton.setText(formatedDate);
                            pickedMonth = selectedmonth + 1;
                            pickedDay = selectedday;
                            pickedYear = selectedyear;
                        }
                    }, pickedYear, pickedMonth - 1, pickedDay);
                    mDatePicker.setTitle("Select Date");
                    mDatePicker.show();
                } else {
                    DatePickerDialog mDatePicker = new DatePickerDialog(EditEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedyear, int selectedmonth, int selectedday) {
                            formatedDate = df.format(new Date(selectedyear - 1900, selectedmonth, selectedday));
                            dateButton.setText(formatedDate);
                            pickedMonth = selectedmonth + 1;
                            currentEvent.setMonth(selectedmonth + 1);
                            pickedDay = selectedday;
                            currentEvent.setDay(selectedday);
                            pickedYear = selectedyear;
                            currentEvent.setYear(selectedyear);
                        }
                    }, currentEvent.getYear(), currentEvent.getMonth() - 1, currentEvent.getDay());
                    mDatePicker.setTitle("Select Date");
                    mDatePicker.show();
                }
            }
        });
    }

    /**
     * This method initiates some layout variables.
     */

    private void initLayoutStuff() {
        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);
        finishButton = (Button) findViewById(R.id.finishButton);
        titleEditText = (EditText) findViewById(R.id.title);
    }

    /**
     * This method initiates the values of the date
     * and time buttons.
     */

    private void initValues() {
        cal = Calendar.getInstance();
        df = new SimpleDateFormat("dd - MMM - yyyy");
        tf = new SimpleDateFormat("HH : mm");

        if (currentEvent == null)
        {
            formatedDate = df.format(cal.getTime());
            formatedTime = tf.format(cal.getTime());
            pickedMinute = cal.get(Calendar.MINUTE);
            pickedMonth = cal.get(Calendar.MONTH) + 1;
            pickedYear = cal.get(Calendar.YEAR);
            pickedHour = cal.get(Calendar.HOUR);
            pickedDay = cal.get(Calendar.DAY_OF_MONTH);
        }

        else
        {
            Date date = new Date(currentEvent.getYear() - 1900, currentEvent.getMonth() - 1, currentEvent.getDay(), currentEvent.getHour(), currentEvent.getMinute());
            formatedDate = df.format(date);
            formatedTime = tf.format(date);
            pickedMinute = currentEvent.getMinute();
            pickedHour = currentEvent.getHour();
            pickedDay = currentEvent.getDay();
            pickedMonth = currentEvent.getMonth() - 1;
            pickedYear = currentEvent.getYear() - 1900;
            titleEditText.setText(currentEvent.getTitle().toString());
        }
        dateButton.setText(formatedDate);
        timeButton.setText(formatedTime);

    }




}
