package webserver667.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

import webserver667.constant.Constants;

/**
 * @author 7991uxug@gmail.com
 * @date 2/23/24 8:12 PM
 */
public class TimeStampUtil {
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
}
