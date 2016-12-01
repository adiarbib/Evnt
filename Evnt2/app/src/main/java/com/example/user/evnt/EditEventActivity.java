package com.example.user.evnt;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

    int hour;
    int minute;
    int mYear;
    int mMonth;
    int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        initLayoutStuff();

        onDateButtonClicked();

        onTimeButtonClicked();

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void onTimeButtonClicked() {
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hour = cal.get(Calendar.HOUR_OF_DAY);
                minute = cal.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(EditEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        timeButton.setText(selectedHour + " : " + selectedMinute);
                    }
                }, hour, minute, true);

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private void onDateButtonClicked() {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(EditEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedyear, int selectedmonth, int selectedday) {
                        dateButton.setText(selectedday + " / " + selectedmonth + " / " + selectedyear);
                    }
                }, mYear, mMonth, mDay);

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
