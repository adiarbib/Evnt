package com.example.user.evnt;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * this is the edit event activity
 */
public class EditEventActivity extends AppCompatActivity {

    EditText titleEditText;
    Button dateButton;
    Button timeButton;
    Button finishButton;
    Calendar cal;
    String formattedDate;
    String formatedTime;
    MyEvent currentEvent;
    int positionOfCurrentEvent;

    int pickedHour;
    int pickedMinute;
    int pickedYear;
    int pickedMonth;
    int pickedDay;

    EventManagment eventManagment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        initLayoutStuff();

        positionOfCurrentEvent = getIntent().getIntExtra(MainActivity.CURRENT_EVENT_POSITION, positionOfCurrentEvent);
        eventManagment = new ParseActions();
        eventManagment.retrieveEventsCallback(new FindCallback<MyEvent>() {
            @Override
            public void done(List<MyEvent> list, ParseException e) {
                Collections.sort(list);
                currentEvent = list.get(positionOfCurrentEvent);
                initValues();

            }
        });
        initDateButton();
        initTimeButton();
        initDoneButton();
    }

    private void initDoneButton() {
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyEvent newEvent = new MyEvent();
                newEvent.setDay(pickedDay);
                newEvent.setMonth(pickedMonth);
                newEvent.setYear(pickedYear);
                newEvent.setHour(pickedHour);
                newEvent.setMinute(pickedMinute);
                newEvent.setTitle(titleEditText.getText().toString());
                eventManagment.updateEvent(newEvent);
                Intent backToMainActivity = new Intent(EditEventActivity.this, MainActivity.class);
                startActivity(backToMainActivity);
            }
        });
    }

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
                    }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);

                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                } else {
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(EditEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            timeButton.setText(selectedHour + " : " + selectedMinute);
                            pickedHour = selectedHour;
                            pickedMinute = selectedMinute;
                        }
                    }, currentEvent.getHour(), currentEvent.getMinute(), true);

                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            }
        });
    }

    private void initDateButton() {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentEvent == null) {
                    DatePickerDialog mDatePicker = new DatePickerDialog(EditEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedyear, int selectedmonth, int selectedday) {
                            dateButton.setText(selectedday + " / " + selectedmonth + " / " + selectedyear);
                            pickedMonth = selectedmonth + 1;
                            pickedDay = selectedday;
                            pickedYear = selectedyear;
                        }
                    }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                    mDatePicker.setTitle("Select Date");
                    mDatePicker.show();
                } else {
                    DatePickerDialog mDatePicker = new DatePickerDialog(EditEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedyear, int selectedmonth, int selectedday) {
                            dateButton.setText(selectedday + " / " + selectedmonth + " / " + selectedyear);
                            pickedMonth = selectedmonth + 1;
                            pickedDay = selectedday;
                            pickedYear = selectedyear;
                        }
                    }, currentEvent.getYear(), currentEvent.getMonth() - 1, currentEvent.getDay());
                    mDatePicker.setTitle("Select Date");
                    mDatePicker.show();
                }
            }
        });
    }

    private void initLayoutStuff() {
        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);
        finishButton = (Button) findViewById(R.id.finishButton);
        titleEditText = (EditText) findViewById(R.id.title);
    }

    private void initValues() {
        cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd - MMM - yyyy");
        SimpleDateFormat tf = new SimpleDateFormat("HH : mm");

        if (currentEvent == null) {
            Toast.makeText(EditEventActivity.this, "sup",
                    Toast.LENGTH_LONG).show();
            formattedDate = df.format(cal.getTime());
            formatedTime = tf.format(cal.getTime());
            pickedMinute = cal.get(Calendar.MINUTE);
            pickedMonth = cal.get(Calendar.MONTH) + 1;
            pickedYear = cal.get(Calendar.YEAR);
            pickedHour = cal.get(Calendar.HOUR);
            pickedDay = cal.get(Calendar.DAY_OF_MONTH);
        } else {

            Date date = new Date(currentEvent.getYear() - 1900, currentEvent.getMonth() - 1, currentEvent.getDay(), currentEvent.getHour(), currentEvent.getMinute());
            formattedDate = df.format(date);
            formatedTime = tf.format(date);
            pickedMinute = currentEvent.getMinute();
            pickedHour = currentEvent.getHour();
            pickedDay = currentEvent.getDay();
            pickedMonth = currentEvent.getMonth() - 1;
            pickedYear = currentEvent.getYear() - 1900;
            titleEditText.setText(currentEvent.getTitle().toString());
        }
        dateButton.setText(formattedDate);
        timeButton.setText(formatedTime);

    }


}
