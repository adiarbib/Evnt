package com.example.user.evnt;

import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by User on 27/11/2016.
 */
public class MyEvent extends ParseObject
{
    public int getYear() {
        return getInt("year");
    }

    public void setYear(int year) {
        put("year", year);
    }

    public int getMonth() {
        return getInt("month");
    }

    public void setMonth(int month) {
        put("month",month);
    }

    public int getDay() {
        return getInt("day");
    }

    public void setDay(int day) {
        put("day",day);
    }

    public int getHour() {
        return getInt("hour");
    }

    public void setHour(int hour) {
        put("hour",hour);
    }

    public int getMinute() {
        return getInt("minute");
    }

    public void setMinute(int minute) {
        put("minute",minute);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public static ParseQuery<MyEvent> getQuery() {
        return ParseQuery.getQuery(MyEvent.class);
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
