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

import java.util.Calendar;

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
        initDateButton();
        initTimeButton();
        eventManagment=new ParseActions();
        initDoneButton();
    }

    private void initDoneButton() {
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyEvent newEvent=new MyEvent();
                newEvent.setDay(pickedDay);
                newEvent.setMonth(pickedMonth);
                newEvent.setYear(pickedYear);
                newEvent.setHour(pickedHour);
                newEvent.setMinute(pickedMinute);
                newEvent.setTitle(titleEditText.getText().toString());
                eventManagment.updateEvent(newEvent);
                Intent backToMainActivity=new Intent(EditEventActivity.this,MainActivity.class);
                startActivity(backToMainActivity);
            }
        });
    }

    private void initTimeButton() {
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeButton.setText(selectedHour + " : " + selectedMinute);
                        pickedHour=selectedHour;
                        pickedMinute=selectedMinute;
                    }
                }, cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE), true);

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private void initDateButton() {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(EditEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedyear, int selectedmonth, int selectedday) {
                        dateButton.setText(selectedday + " / " + selectedmonth + " / " + selectedyear);
                        pickedMonth=selectedmonth;
                        pickedDay=selectedday;
                        pickedYear=selectedyear;
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
    }

    private void initLayoutStuff() {
        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);
        finishButton = (Button) findViewById(R.id.finishButton);
        titleEditText = (EditText) findViewById(R.id.title);
        cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd - MMM - yyyy");
        SimpleDateFormat tf = new SimpleDateFormat("HH : mm");
        formattedDate = df.format(cal.getTime());
        dateButton.setText(formattedDate);
        formatedTime = tf.format(cal.getTime());
        timeButton.setText(formatedTime);
    }




}
