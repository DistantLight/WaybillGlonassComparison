package com.ngtu.WaybillGlonassComparison.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class DateConverter {
    private final static String JAVA_FORMAT = "E MMM d hh:mm:ss yyyy";
    private final static String EXCEL_FORMAT = "dd.MM.yyyy";
    private final static String SQL_FORMAT = "yyyy-MM-dd";

    public static Date convertFromJavaToSql(String stringDate) {
        try {
            stringDate = stringDate.replace("MSK ", "");
            SimpleDateFormat dateFormat = new SimpleDateFormat(JAVA_FORMAT, Locale.ENGLISH);
            Date date = dateFormat.parse(stringDate);
            dateFormat.applyPattern(SQL_FORMAT);
            String stringSqlDate = dateFormat.format(date);
            return dateFormat.parse(stringSqlDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date convertFromExcelToSql(String stringDate) {
        try {
            stringDate = stringDate.replace(" ","");
            SimpleDateFormat dateFormat = new SimpleDateFormat(EXCEL_FORMAT, Locale.ENGLISH);
            Date date = dateFormat.parse(stringDate);
            dateFormat.applyPattern(SQL_FORMAT);
            String stringSqlDate = dateFormat.format(date);
            return dateFormat.parse(stringSqlDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date getDateByString(String stringDate) {
        String dateString = "Sun Feb 19 00:00:00 MSK 2023";
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
