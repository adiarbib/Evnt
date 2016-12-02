package com.example.user.evnt;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by User on 27/11/2016.
 */
@ParseClassName("MyEvent")
public class MyEvent extends ParseObject
{

    //all constant keys
    public static final String YEAR_KEY="year";
    public static final String MONTH_KEY="month";
    public static final String DAY_KEY="day";
    public static final String HOUR_KEY="hour";
    public static final String MINUTE_KEY="minute";
    public static final String TITLE_KEY="title";
    public static final String DATE_KEY="date";

    public MyEvent()
    {
        super();
    }

    public int getYear() {
        return getInt(YEAR_KEY);
    }

    public void setYear(int year) {
        put(YEAR_KEY, year);
    }

    public int getMonth() {
        return getInt(MONTH_KEY);
    }

    public void setMonth(int month) {
        put(MONTH_KEY,month);
    }

    public int getDay() {
        return getInt(DAY_KEY);
    }

    public void setDay(int day) {
        put(DAY_KEY,day);
    }

    public int getHour() {
        return getInt(HOUR_KEY);
    }

    public void setHour(int hour) {
        put(HOUR_KEY,hour);
    }

    public int getMinute() {
        return getInt(MINUTE_KEY);
    }

    public void setMinute(int minute) {
        put(MINUTE_KEY,minute);
    }

    public String getTitle() {
        return getString(TITLE_KEY);
    }

    public void setTitle(String title) {
        put(TITLE_KEY, title);
    }

    @Override
    public String toString() {
        String result=
                this.getTitle()+" on "+
                this.getDay()+" . "+
                this.getMonth()+" . "+
                this.getYear()+" at "+
                        getHour()+" : "+
                        getMinute()+'\n';
        return result;
    }
}
