/**
 * Package: moe.zzy040330.smbms.utils
 * File: DateUtils.java
 * Author: Ziyu ZHOU
 * Date: 05/01/2025
 * Time: 13:24
 * Description: Date utils, for converting Date object and String.
 */
package moe.zzy040330.smbms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    /**
     * Convert Date to yyyy-MM-dd String
     *
     * @param date Date object
     * @return yyyy-MM-dd String
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return formatter.format(date);
    }

    /**
     * Parse String (yyyy-MM-dd) to Date object
     *
     * @param dateStr yyyy-MM-dd formatted String
     * @return Date object, will be null if empty.
     */
    public static Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}