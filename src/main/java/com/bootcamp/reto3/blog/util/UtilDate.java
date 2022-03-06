package com.bootcamp.reto3.blog.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;


public class UtilDate {

    public static long getDifferenceDays(Date d1, Date d2) {
        return ChronoUnit.YEARS.between(convertToLocalDate(d1), convertToLocalDate(d2));
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date getNowDate() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getTimeZone("America/Lima"));
        String dateString = df.format(date);

        Date parse = null;
        try {
            parse = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }
}
