package com.example.user.evnt;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import java.io.Serializable;

/**
 * Parse object MyEvent
 */
@ParseClassName("MyEvent")
public class MyEvent extends ParseObject implements Comparable<MyEvent>,Serializable {

    /**
     * constant keys
     */
    public static final String YEAR_KEY = "year";
    public static final String MONTH_KEY = "month";
    public static final String DAY_KEY = "day";
    public static final String HOUR_KEY = "hour";
    public static final String MINUTE_KEY = "minute";
    public static final String TITLE_KEY = "title";

    public MyEvent() {
        super();
    }

    public int getYear() {
        return getInt(YEAR_KEY);
    }

    /**
     *
     * @param year the year of the event
     */

    public void setYear(int year) {
        put(YEAR_KEY, year);
    }

    public int getMonth() {
        return getInt(MONTH_KEY);
    }

    /**
     *
     * @param month the month of the event
     */

    public void setMonth(int month) {
        put(MONTH_KEY, month);
    }

    public int getDay() {
        return getInt(DAY_KEY);
    }

    /**
     *
     * @param day the day of the event
     */

    public void setDay(int day) {
        put(DAY_KEY, day);
    }

    public int getHour() {
        return getInt(HOUR_KEY);
    }

    /**
     *
     * @param hour the hour of the event
     */

    public void setHour(int hour) {
        put(HOUR_KEY, hour);
    }

    public int getMinute() {
        return getInt(MINUTE_KEY);
    }

    /**
     *
     * @param minute the minute of the event
     */

    public void setMinute(int minute) {
        put(MINUTE_KEY, minute);
    }

    public String getTitle() {
        return getString(TITLE_KEY);
    }

    /**
     *
     * @param title the title of the event
     */

    public void setTitle(String title) {
        put(TITLE_KEY, title);
    }

    @Override
    public String toString() {
        String result =
                this.getTitle() + " on " +
                        this.getDay() + " . " +
                        this.getMonth() + " . " +
                        this.getYear() + " at " +
                        getHour() + " : " +
                        getMinute() + '\n';
        return result;
    }

    /**
     * compares between the date of the events. and returns which one is closer
     * @param compareEvent
     * @return
     */


    @Override
    public int compareTo(MyEvent compareEvent) {
        if (compareEvent.getYear() != this.getYear()) {
            if (compareEvent.getYear() > this.getYear())
                return -1;
            else return 1;
        } else if (compareEvent.getMonth() != this.getMonth()) {
            if (compareEvent.getMonth() > this.getMonth())
                return -1;
            else return 1;
        } else if (compareEvent.getDay() != this.getDay()) {
            if (compareEvent.getDay() > this.getDay())
                return -1;
            else return 1;
        } else if (compareEvent.getHour() != this.getHour()) {
            if (compareEvent.getHour() > this.getHour())
                return -1;
            else return 1;
        } else if (compareEvent.getMinute() > this.getMinute()) {
            if (compareEvent.getMinute() > this.getMinute())
                return -1;
            else return 1;
        } else if (!compareEvent.getTitle().equals(this.getTitle())) {
            if (this.getTitle().compareTo(compareEvent.getTitle()) < 0)
                return -1;
            else return 1;
        }
        return 0;
    }
}
