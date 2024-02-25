package webserver667.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import webserver667.constant.Constants;

/**
 * @author 7991uxug@gmail.com
 * @date 2/23/24 8:12 PM
 */
public class TimestampUtil {
    public static long convertToTimestamp(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.RFC7321_PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static String convertFromTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneOffset.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.RFC7321_PATTERN);
        return formatter.format(zonedDateTime);
    }

    private static String getCurrentTimeByPattern(String pattern) {
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);

        return now.format(formatter);
    }

    public static String getCurrentTimeInCLFPattern() {
        return getCurrentTimeByPattern(Constants.CLF_PATTERN);
    }

    public static String getCurrentTimeRFC7321() {
        return getCurrentTimeByPattern(Constants.RFC7321_PATTERN);
    }

    public static void main(String[] args) {
        System.out.println(getCurrentTimeRFC7321());
    }
}
