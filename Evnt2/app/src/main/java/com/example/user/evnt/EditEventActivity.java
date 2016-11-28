package com.example.user.evnt;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * this is the edit event activity
 */

public class EditEventActivity extends AppCompatActivity {

    public static final String APPLICATION_ID = "a2IxzIPAzFaelatZwzP038qFhKIdLIuAc4tGsLET";
    public static final String CLIENT_KEY = "HJwXRtCY87SyafpzsnJOapBdQOknRjwqBNzRRgCG";
    public static final String SERVER = "https://parseapi.back4app.com";

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

        initParse(this);

        initLayoutStuff();

        onDateButtonClicked();

        onTimeButtonClicked();

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public static void initParse(Context context) {
        ParseObject.registerSubclass(MyEvent.class);
        Parse.enableLocalDatastore(context);
        Parse.initialize(new Parse.Configuration.Builder(context)
                .applicationId(APPLICATION_ID)
                .clientKey(CLIENT_KEY)
                .server(SERVER)
                .build()
        );
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

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
