package com.scarlesh.slashouse.gw2tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class GWToolDate {
    static final int DAY = 1440;

    public static double getTimeZoneDiff (){
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        int mGMTOffset = mTimeZone.getRawOffset();
        return (int) TimeUnit.HOURS.convert(mGMTOffset, TimeUnit.MILLISECONDS);
    }
    public static double getTimeZoneDiffMillis (){
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        int mGMTOffset = mTimeZone.getRawOffset();
        return (int) TimeUnit.HOURS.convert(mGMTOffset, TimeUnit.MILLISECONDS);
    }

    public static double sumDates(double currentTm, double spawn) throws ParseException {
        int ms_to_min = 60 * 1000;
        String fd = String.valueOf(currentTm);
        String sd = String.valueOf(spawn);
        SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
        long first_date = sdf.parse(fd).getTime();
        long second_date = sdf.parse(sd).getTime();
        double delta_min = ( second_date - first_date ) / ms_to_min;
        if (delta_min < 0)
            delta_min += DAY;
        return delta_min;
    }

    public static String sumMinutes(String start, String duration){
        // For some reason there is an hour offset, I will test with other TZ
        // I am manually adding an hour
        long h_in_ms = 3600000;
        double tz_delta = GWToolDate.getTimeZoneDiffMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
        long date_1 = 0;
        long date_2 = 0;
        try {
            date_1 = sdf.parse(start).getTime();
            date_2 = sdf.parse(duration).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(new Date(date_1 + date_2 + h_in_ms));
    }
}
